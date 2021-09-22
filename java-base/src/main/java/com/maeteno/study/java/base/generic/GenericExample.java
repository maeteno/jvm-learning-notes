package com.maeteno.study.java.base.generic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Alan.Fu
 */
@Slf4j
public class GenericExample {

    public static void main(String[] args) {
        GenericDemo<ClassB> genericDemo2 = new GenericDemo<>();
        genericDemo2.print(new ClassB());
        genericDemo2.print(new ClassC());
        genericDemo2.print(new ClassC2());
        // genericDemo2.print(new ClassA());

//        GenericDemo<? extends ClassB> genericDemo3 = new GenericDemo<>();
//        genericDemo3.print(new ClassB());
//        genericDemo3.print(new ClassC());
//        genericDemo3.print(new ClassC2());
//        genericDemo3.print(new ClassD());
//        genericDemo3.print(new ClassA());


        GenericDemo<ClassC> genericDemo4 = new GenericDemo<>();
        // genericDemo4.print(new ClassB());
        genericDemo4.print(new ClassC());
        genericDemo4.print(new ClassD());
        // genericDemo4.print(new ClassC2());
        // genericDemo4.print(new ClassA());

        GenericDemo<? super ClassC> genericDemo1 = new GenericDemo<>();
        //genericDemo1.print(new ClassB());
        genericDemo1.print(new ClassC());
        genericDemo1.print(new ClassD());
        // genericDemo1.print(new ClassC2());
        // genericDemo1.print(new ClassA());
    }
}
