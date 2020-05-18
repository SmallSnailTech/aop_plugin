package com.snail.aop;
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class AopPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println(" Aha, let's do it !!! ")
        if (!project.android) {
            throw new IllegalStateException('please apply me to the android project !!!')
        }

        def app = project.plugins.withType(AppPlugin)
        def library = project.plugins.withType(LibraryPlugin)
        if (!app && !library) {
            throw new IllegalStateException("'only support android application or library.")
        }

        final def log = project.logger
        final def variants
        if (app) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.dependencies {
            implementation 'org.aspectj:aspectjrt:1.9.1'
        }

        variants.all { variant ->
            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(
                        File.pathSeparator)]

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler)

                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }
    }
}