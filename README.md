# aop_plugin
A convenient plugin to integrate AOP into an Android project

# How to use
### 1. add maven url to the build.gradle of your root project

```
buildscript {
    
    repositories {
        google()
            maven {
                url uri('snail_aop_plugin')
            }
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.1'
        //add classpath for your project
        classpath 'com.snail.aop:aop_plugin:1.0.0'
        
    }
}

```

### 2. add below config to the build.gradle of your app module or library module

```
apply plugin: 'aop_plugin'

```

### 3. now,you can use AOP to do what you want to do

demo:
```

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TestViewInflate {

    @Around("call(* android.app.Activity.setContentView(..))")
    public void setContentViewTest(ProceedingJoinPoint point){
        long start = System.currentTimeMillis();
        Signature signature = point.getSignature();
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.d("AOP",point.getTarget().getClass().getName()
                +" setContentView total time:"+(System.currentTimeMillis() - start));
    }


    @Around("execution(* android.app.Activity.on**(..))")
    public void activityOnMethodTest(ProceedingJoinPoint point)throws Throwable{
        Log.d("AOP"," activityOnMethodTest aop test:"+point.toShortString());
        point.proceed();
    }

    @Around("call(* android.view.View.measure(..))")
    public void measureTimeTest(ProceedingJoinPoint point)throws Throwable{
        Log.d("AOP"," measureTimeTest total time:");
    }

}


```
