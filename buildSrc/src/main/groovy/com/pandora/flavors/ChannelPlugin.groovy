package com.pandora.flavors

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class ChannelPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println '这是插件的入口: ' + project.name

        //添加扩展属性
        project.extensions.add("platform", Platform.class)

        project.afterEvaluate {
            println 'afterEvaluate 在这里处理多渠道打包...'
            //获取扩展属性值--渠道属性
            Platform platform = project.platform
            //Platform platform = project.getExtensions().getByType(Platform.class)
            //Platform platform = project.getExtensions().getByName('platform')

            //资源路径
            def resourceDir = platform.resourceDir
            //应用名称
            def appName = platform.appName

            println "资源路径: ${resourceDir}"
            println "应用名称: ${appName}"

            //获取任务当前任务名称
            //非打包时不做任何操作
            def startParameter = project.gradle.startParameter
            def targetTasks = startParameter.taskNames

            //遍历
            targetTasks.each {
                //不是打包任务
                if (!it.contains("assemble") && !it.contains("aR")) {
                    return
                }
            }

            //如果是打包任务
            project.android.applicationVariants.all { variant ->
                String variantName = variant.name.capitalize()
                def variantFlavorName = variant.flavorName
                println "variantName: ${variantName}"
                println "variantFlavorName: ${variantFlavorName}"

                //执行打包的第一个task任务,preBuild
                Task preBuild = project.tasks["pre${variantName}Build"]
                if (variantFlavorName == null || "" == variantFlavorName) {
                    return
                }

                println "${preBuild.name} ,打包任务..."

                //在preBuild任务之前,拷贝文件
                preBuild.doFirst {
                    println "${variantFlavorName} 在preBuild任务之前,拷贝文件开始..."
                    project.copy {
                        from "../${resourceDir}/${variantFlavorName}"
                        into "../${appName}/src/main/res"
                    }
                    println "${variantFlavorName} 渠道资源已经拷贝完成..."
                }
            }
        }
    }
}