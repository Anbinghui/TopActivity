package com.smallan.topactivity;

/**
 * Created by An on 2017/12/26.
 */

public class TopContentBean {

    private String className;
    private String packageName;

    public TopContentBean(String className, String packageName) {
        this.className = className;
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "TopContentBean{" +
                "className='" + className + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
