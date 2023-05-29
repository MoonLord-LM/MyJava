package cn.moonlord.common.lombok;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;

import java.util.List;

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
        newSource.append(NEW_LINE);

        // 类的递归处理
        for (Node child : children) {
            if (child instanceof ClassOrInterfaceDeclaration) {
                newSource.append(new ClassCleaner((ClassOrInterfaceDeclaration) child).toString().trim());
            }
        }
        newSource.append(NEW_LINE);

        return newSource.toString();
    }

    public static class ClassCleaner {

        private final ClassOrInterfaceDeclaration sourceNode;
        private final List<Node> children;

        public ClassCleaner(ClassOrInterfaceDeclaration sourceNode) {
            this.sourceNode = sourceNode;
            this.children = sourceNode.getChildNodes();
        }
        
        public boolean containsChildType(Class<?> clazz){
            for (Node child : children) {
                if (child.getClass() == clazz) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder newSource = new StringBuilder();

            // 注释
            if (sourceNode.getComment().isPresent()) {
                newSource.append(sourceNode.getComment().get());
                newSource.append(NEW_LINE);
            }

            // 类的注解
            for (Node child : children) {
                if (child instanceof MarkerAnnotationExpr) {
                    newSource.append(child);
                    newSource.append(NEW_LINE);
                }
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
                    newSource.append(" ");
                    newSource.append("{");
                    newSource.append(NEW_LINE);
                }
            }
            newSource.append(NEW_LINE);

            // 类的变量
            for (Node child : children) {
                if (child instanceof FieldDeclaration) {
                    if(((FieldDeclaration) child).isStatic()) {
                        newSource.append("    ");
                        newSource.append(child.toString().trim().replace("\n", "\n" + SPACE_INDENT));
                        newSource.append(NEW_LINE);
                    }
                }
            }
            for (Node child : children) {
                if (child instanceof FieldDeclaration) {
                    if(!((FieldDeclaration) child).isStatic()) {
                        newSource.append("    ");
                        newSource.append(child.toString().trim().replace("\n", "\n" + SPACE_INDENT));
                        newSource.append(NEW_LINE);
                    }
                }
            }
            if(containsChildType(FieldDeclaration.class)) {
                newSource.append(NEW_LINE);
            }

            // 类的方法
            for (Node child : children) {
                if (child instanceof MethodDeclaration) {
                    newSource.append("    ");
                    newSource.append(child.toString().trim().replace("\n", "\n" + SPACE_INDENT));
                    newSource.append(NEW_LINE);
                    newSource.append(NEW_LINE);
                }
            }

            newSource.append("}");
            newSource.append(NEW_LINE);
            return newSource.toString();
        }
    }

}
