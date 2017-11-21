package com.pokercc.appinjector.compiler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class AppInjectProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elememts = roundEnvironment.getElementsAnnotatedWith(AppInject.class);
        //使用butterKnife 的方法，生成一个binder类，来给被AppInject 注解的field 赋值
        //类名和binder 类的关系，是通过类名加后缀这种规则找到的，这种类名是不会被混淆的。binder 也设置了防止混淆。
        
        return false;
    }
}
