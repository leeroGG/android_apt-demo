package android.smartahc.com.apt_processor;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by Leero on 2018/12/12.
 * Desc:
 */
public class ClassCreatorProxy {
    private String mBindingClassName;
    private String mPackageName;
    private TypeElement mTypeElement;
    private Map<Integer, VariableElement> mVariableElementMap = new HashMap<>();

    public ClassCreatorProxy(Elements elementUtils, TypeElement mTypeElement) {
        this.mTypeElement = mTypeElement;

        PackageElement packageElement = elementUtils.getPackageOf(mTypeElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = mTypeElement.getSimpleName().toString();
        this.mPackageName = packageName;
        this.mBindingClassName = className + "_ViewBinding";
    }

    public void putElement(int id, VariableElement element) {
        mVariableElementMap.put(id, element);
    }

    // generate java code
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(mPackageName).append(";\n\n");
        builder.append("import android.smartahc.com.apt_library.*;\n");
        builder.append("\n");
        builder.append("public class ").append(mBindingClassName).append(" {\n");

        // add method
        generateMethods(builder);
        builder.append("\n");
        builder.append("}\n");
        return builder.toString();
    }

    // build up method
    private void generateMethods(StringBuilder builder) {
        builder.append("public void bind(").append(mTypeElement.getQualifiedName()).append(" host) {\n");
        for (int id: mVariableElementMap.keySet()) {
            VariableElement element = mVariableElementMap.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            builder.append("host.").append(name).append(" = ");
            builder.append("(").append(type).append(")(((android.app.Activity)host).findViewById(").append(id).append("));\n");
        }
        builder.append("}\n");
    }

    public String getProxyClassFullName() {
        return mPackageName + "." + mBindingClassName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }
}
