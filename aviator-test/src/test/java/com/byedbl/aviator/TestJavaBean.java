package com.byedbl.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.*;

public class TestJavaBean {

    int i;
    float f;
    Date date;
    List<Person> persons;
    // 构造方法
    public TestJavaBean(int i, float f, Date date) {
        this.i = i;
        this.f = f;
        this.date = date;
    }
    // getter and setter

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public static void main(String[] args) {
        TestJavaBean foo = new TestJavaBean(100, 3.14f, new Date());
        Person person = foo.new Person();
        person.setName("name1");
        List<Person> people = new ArrayList<>();
        people.add(person);
        foo.setPersons(people);

        Map<String, Object> env = new HashMap<String, Object>();
        env.put("foo", foo);
        System.out.println(AviatorEvaluator.execute("'foo.i = '+foo.i", env));   // foo.i = 100
        System.out.println(AviatorEvaluator.execute("'foo.f = '+foo.f", env));   // foo.f = 3.14
        System.out.println(AviatorEvaluator.execute("'foo.date.year = '+(foo.date.year+1990)", env));  // foo.date.year = 2106
        System.out.println(AviatorEvaluator.execute("'foo.persons[0].name = '+(#foo.persons[0].name)", env));  // foo.date.year = 2106
    }

    public class Person{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
