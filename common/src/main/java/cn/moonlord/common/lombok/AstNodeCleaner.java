package cn.moonlord.common.lombok;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class AstNodeCleaner {

    private static final String NEW_LINE = "\r\n";
    private static final String SPACE_INDENT = "    ";

    private final Node sourceNode;

    public AstNodeCleaner(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    @Override
    public String toString() {
        StringBuilder newSource = new StringBuilder();
        List<Node> children = sourceNode.getChildNodes();

        // 注释
        if (sourceNode.getComment().isPresent()) {
            newSource.append(sourceNode.getComment().get().toString().trim());
            newSource.append(NEW_LINE);
        }

        // 包路径
        for (Node child : children) {
            if (child instanceof PackageDeclaration) {
                newSource.append(NEW_LINE);
                newSource.append(child.toString().trim());
                newSource.append(NEW_LINE);
            }
        }
        newSource.append(NEW_LINE);

        // 引用
        for (Node child : children) {
            if (child instanceof ImportDeclaration) {
                newSource.append(child.toString().trim());
                newSource.append(NEW_LINE);
            }
        }
        // 补充 lombok 的引用
        if (!newSource.toString().contains("import lombok.Getter;")) {
            newSource.append("import lombok.Getter;");
            newSource.append(NEW_LINE);
        }
        if (!newSource.toString().contains("import lombok.Setter;")) {
            newSource.append("import lombok.Setter;");
            newSource.append(NEW_LINE);
        }
        newSource.append(NEW_LINE);

        // 类的递归处理
        for (Node child : children) {
            if (child instanceof ClassOrInterfaceDeclaration) {
                newSource.append(new ClassNodeCleaner((ClassOrInterfaceDeclaration) child).toString().trim());
                newSource.append(NEW_LINE);
                newSource.append(NEW_LINE);
            }
        }

        // 多余的空格处理
        String result = newSource.toString();
        while (result.contains(NEW_LINE + SPACE_INDENT + NEW_LINE)) {
            result = result.replace(NEW_LINE + SPACE_INDENT + NEW_LINE, NEW_LINE + NEW_LINE);
        }
        return result;
    }

    public static class ClassNodeCleaner {

        private final ClassOrInterfaceDeclaration sourceNode;
        private final List<Node> children;

        public ClassNodeCleaner(ClassOrInterfaceDeclaration sourceNode) {
            this.sourceNode = sourceNode;
            this.children = sourceNode.getChildNodes();
        }

        public boolean containsChildType(Class<?> clazz) {
            for (Node child : children) {
                if (child.getClass() == clazz) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            if(sourceNode.isInterface()){
                return sourceNode.toString();
            }

            StringBuilder newSource = new StringBuilder();

            // 注释
            if (sourceNode.getComment().isPresent()) {
                newSource.append(sourceNode.getComment().get());
            }

            // 类的注解
            for (Node child : children) {
                if (child instanceof MarkerAnnotationExpr) {
                    newSource.append(child);
                    newSource.append(NEW_LINE);
                }
            }
            // 补充 lombok 的注解
            if (!newSource.toString().contains("@Getter")) {
                newSource.append("@Getter");
                newSource.append(NEW_LINE);
            }
            if (!newSource.toString().contains("@Setter")) {
                newSource.append("@Setter");
                newSource.append(NEW_LINE);
            }

            // 类的修饰符
            for (Node child : children) {
                if (child instanceof Modifier) {
                    newSource.append(child.toString().trim());
                    newSource.append(" ");
                }
            }

            // 类的名称
            for (Node child : children) {
                if (child instanceof SimpleName) {
                    newSource.append("class");
                    newSource.append(" ");
                    newSource.append(child.toString().trim());
                }
            }

            // 类的泛型
            if (sourceNode.getTypeParameters().size() > 0) {
                newSource.append("<");
            }
            for (Node type : sourceNode.getTypeParameters()) {
                newSource.append(type.toString());
                newSource.append(",");
            }
            if (sourceNode.getTypeParameters().size() > 0) {
                newSource.deleteCharAt(newSource.length() - 1);
                newSource.append(">");
            }

            // 类的继承父类
            if (sourceNode.getExtendedTypes().size() > 0) {
                newSource.append(" ");
                newSource.append("extends");
            }
            for (Node extended : sourceNode.getExtendedTypes()) {
                newSource.append(" ");
                newSource.append(extended.toString());
                newSource.append(",");
            }
            if (sourceNode.getExtendedTypes().size() > 0) {
                newSource.deleteCharAt(newSource.length() - 1);
            }

            // 类的实现接口
            if (sourceNode.getImplementedTypes().size() > 0) {
                newSource.append(" ");
                newSource.append("implements");
            }
            for (Node implemented : sourceNode.getImplementedTypes()) {
                newSource.append(" ");
                newSource.append(implemented.toString());
                newSource.append(",");
            }
            if (sourceNode.getImplementedTypes().size() > 0) {
                newSource.deleteCharAt(newSource.length() - 1);
            }

            newSource.append(" ");
            newSource.append("{");
            newSource.append(NEW_LINE);
            newSource.append(NEW_LINE);

            // 类的枚举
            for (Node child : children) {
                if (child instanceof EnumDeclaration) {
                    newSource.append(SPACE_INDENT);
                    newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                    newSource.append(NEW_LINE);
                }
            }

            // 类的变量
            for (Node child : children) {
                if (child instanceof FieldDeclaration) {
                    FieldDeclaration field = (FieldDeclaration) child;
                    if (field.isStatic()) {
                        newSource.append(SPACE_INDENT);
                        newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                        newSource.append(NEW_LINE);
                    }
                }
            }
            HashMap<String, VariableDeclarator> fields = new HashMap<>();
            HashSet<String> fieldNames = new HashSet<>();
            HashSet<String> duplicateFieldNames = new HashSet<>();
            for (Node child : children) {
                if (child instanceof FieldDeclaration) {
                    FieldDeclaration field = (FieldDeclaration) child;
                    if (!field.isStatic()) {
                        VariableDeclarator variable = field.getVariable(0);
                        String fieldName = variable.getNameAsString();

                        // 记录大小写不同的重名变量，这种不能删除方法
                        if(fieldNames.contains(fieldName.toLowerCase(Locale.ROOT))){
                            duplicateFieldNames.add(fieldName.toLowerCase(Locale.ROOT));
                            System.out.println("duplicate: " + fieldName.toLowerCase(Locale.ROOT));
                        }
                        fieldNames.add(fieldName.toLowerCase(Locale.ROOT));

                        String fieldNameUpper = fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
                        System.out.println("variable: " + variable.getType() + " " + variable);
                        if (variable.getType().toString().equals("boolean")) {
                            fields.put("is" + fieldNameUpper, variable);
                        } else {
                            fields.put("get" + fieldNameUpper, variable);
                        }
                        fields.put("set" + fieldNameUpper, variable);
                        newSource.append(SPACE_INDENT);
                        newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                        newSource.append(NEW_LINE);
                    }
                }
            }
            if (containsChildType(FieldDeclaration.class)) {
                newSource.append(NEW_LINE);
            }

            // 类的构造函数
            boolean constructorAdded = false;
            for (Node child : children) {
                if (child instanceof ConstructorDeclaration) {
                    ConstructorDeclaration constructor = (ConstructorDeclaration) child;
                    System.out.println("constructor: " + constructor);
                    newSource.append(SPACE_INDENT);
                    newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                    newSource.append(NEW_LINE);
                    constructorAdded = true;
                }
            }
            if (constructorAdded) {
                newSource.append(NEW_LINE);
            }

            // 类的方法
            boolean methodAdded = false;
            for (Node child : children) {
                if (child instanceof MethodDeclaration) {
                    MethodDeclaration method = (MethodDeclaration) child;
                    String methodName = method.getName().asString();
                    System.out.println("methodName: " + methodName + " " + fields.get(methodName));
                    VariableDeclarator variable = fields.get(methodName);
                    if (variable == null || duplicateFieldNames.contains(variable.getNameAsString().toLowerCase(Locale.ROOT))) {
                        newSource.append(SPACE_INDENT);
                        newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                        newSource.append(NEW_LINE);
                        methodAdded = true;
                    } else {
                        String fieldName = variable.getNameAsString();
                        String fieldType = variable.getType().toString();
                        String blockStmt = method.getBody().get().toString().trim();
                        System.out.println("blockStmt: " + blockStmt);
                        // 是否冗余
                        boolean redundant = false;
                        if (blockStmt.startsWith("{") && blockStmt.endsWith("}")) {
                            blockStmt = blockStmt.substring(1, blockStmt.length() - 1).trim();
                        }
                        if ((methodName.startsWith("is") && fieldType.equals("boolean")) ||
                            (methodName.startsWith("get") && !fieldType.equals("boolean"))) {
                            if (blockStmt.startsWith("return")) {
                                blockStmt = blockStmt.substring("return".length()).trim();
                            }
                            if (blockStmt.startsWith("this.")) {
                                blockStmt = blockStmt.substring("this.".length()).trim();
                            }
                            if (blockStmt.startsWith(fieldName)) {
                                blockStmt = blockStmt.substring(fieldName.length()).trim();
                            }
                            if (blockStmt.startsWith(";")) {
                                blockStmt = blockStmt.substring(";".length()).trim();
                            }
                        } else if (methodName.startsWith("set")) {
                            if (blockStmt.startsWith("this.")) {
                                blockStmt = blockStmt.substring("this.".length()).trim();
                            }
                            if (blockStmt.startsWith(fieldName)) {
                                blockStmt = blockStmt.substring(fieldName.length()).trim();
                            }
                            if (blockStmt.startsWith("=")) {
                                blockStmt = blockStmt.substring("=".length()).trim();
                            }
                            if (blockStmt.startsWith(fieldName)) {
                                blockStmt = blockStmt.substring(fieldName.length()).trim();
                            }
                            if (blockStmt.startsWith(";")) {
                                blockStmt = blockStmt.substring(";".length()).trim();
                            }
                        }
                        if (blockStmt.isEmpty()) {
                            redundant = true;
                        } else {
                            newSource.append(SPACE_INDENT);
                            newSource.append(child.toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                            newSource.append(NEW_LINE);
                            methodAdded = true;
                        }
                    }
                }
            }
            if (methodAdded) {
                newSource.append(NEW_LINE);
            }

            // 类的递归处理
            for (Node child : children) {
                if (child instanceof ClassOrInterfaceDeclaration) {
                    newSource.append(SPACE_INDENT);
                    newSource.append(new ClassNodeCleaner((ClassOrInterfaceDeclaration) child).toString().trim().replace(NEW_LINE, NEW_LINE + SPACE_INDENT));
                    newSource.append(NEW_LINE);
                    newSource.append(NEW_LINE);
                }
            }

            newSource.append("}");
            return newSource.toString();
        }
    }

}
