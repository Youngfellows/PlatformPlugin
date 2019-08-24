package com.pandora.flavors

/**
 * 渠道--扩展属性
 */
class Platform {

    /**
     * 存放渠道包图片的路径
     */
    String resourceDir

    /**
     * 主项目名
     */
    String appName

    Platform() {

    }

    Platform(String resourceDir, String appName) {
        this.resourceDir = resourceDir
        this.appName = appName
    }

    @Override
    public String toString() {
        return "Platform{" +
                "resourceDir='" + resourceDir + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
