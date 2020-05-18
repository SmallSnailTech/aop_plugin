package com.snail.speeddemo.aop;

import android.util.Log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

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
