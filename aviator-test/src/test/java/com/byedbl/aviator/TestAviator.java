package com.byedbl.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;
import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * https://github.com/killme2008/aviator/wiki
 * https://github.com/killme2008/aviator/tree/master/src/test/java/com/googlecode/aviator/example
 */
public class TestAviator {
    @Test
    public void testSimple(){
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        System.out.println(result);
    }

    @Test
    public void testMultiExpression() {
        Object result =  AviatorEvaluator.execute("print('hello world')");
        AviatorEvaluator.setOption(Options.TRACE_EVAL,true);
        System.out.println(result);
        result =  AviatorEvaluator.execute("1+2+3;100-1");
        System.out.println(result);
    }

    @Test
    public void testAviatorEvaluatorInstance() {
        AviatorEvaluatorInstance instance = AviatorEvaluator.newInstance();
        instance.execute("print('hello world')");
    }

    @Test
    public void testConnect() {
        String yourName = "Michael";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("yourName", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        System.out.println(result);
    }

    @Test
    public void testString() {
        System.out.println(AviatorEvaluator.execute(" 'a\"b' "));           // 字符串 a"b
        System.out.println(AviatorEvaluator.execute(" \"a\'b\" "));         // 字符串 a'b
        System.out.println(AviatorEvaluator.execute(" 'hello ' + 3 "));     // 字符串 hello 3
        System.out.println(AviatorEvaluator.execute(" 'hello '+ unknow ")); // 字符串 hello null
    }

    @Test
    public void testExec() {
        String name = "dennis";
        Object exec = AviatorEvaluator.exec(" 'hello ' + yourName ", name);// hello dennis
        System.out.println(exec);

    }

    @Test
    public void testFunction() {
        Long result = (Long) AviatorEvaluator.execute("string.length('hello')");// 5
        System.out.println(result);

    }

    @Test
    public void testFunctionContain() {
        String value = "hell0";
        System.out.println(value.substring(1, 2));
        System.out.println(AviatorEvaluator.execute("string.substring('hello', 1, 2)"));
        System.out.println(AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))"));  // true
    }

    /**
     * lambda (参数1,参数2...) -> 参数体表达式 end
     */
    @Test
    public void testLambda() {
        System.out.println( AviatorEvaluator.exec("(lambda (x,y) -> x + y end)(x,y)", 1, 2));
        System.out.println(AviatorEvaluator.exec("(lambda (x) -> lambda(y) -> lambda(z) -> x + y + z end end end)(1)(2)(3)"));
    }

    class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }
        public String getName() {
            return "add";
        }
    }

    @Test
    public void testDefinedAddFunction() {
        //注册函数
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));           // 3.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)")); // 103.0
    }

    public class GetFirstNonNullFunction extends AbstractVariadicFunction {

        public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
            if (args != null) {
                for (AviatorObject arg : args) {
                    if (arg.getValue(env) != null) {
                        return arg;
                    }
                }
            }
            return new AviatorString(null);
        }


        @Override
        public String getName() {
            return "getFirstNonNull";
        }

    }

    @Test
    public void testGetFirstNonNullFunction() {
        //注册函数
        AviatorEvaluator.addFunction(new GetFirstNonNullFunction());
        System.out.println(AviatorEvaluator.execute("getFirstNonNull(1)"));           // 3.0
        System.out.println(AviatorEvaluator.execute("getFirstNonNull(1,null,2,3)")); // 103.0
//        System.out.println(AviatorEvaluator.execute("getFirstNonNull(null,null,2,3)")); // 103.0
    }

    @Test
    public void overrideFun() {
        AviatorEvaluator.addOpFunction(OperatorType.BIT_AND, new AbstractFunction() {

            @Override
            public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
                return new AviatorString(arg1.getValue(env).toString() + arg2.getValue(env).toString());
            }

            @Override
            public String getName() {
                return "&";
            }
        });
        assertEquals("43", AviatorEvaluator.exec("c&3", 4));
        assertEquals("hello world", AviatorEvaluator.exec("a&' world'", "hello"));
    }

    /**
     *编译表达式
     */
    @Test
    public void compile() {
        String expression = "a-(b-c)>100";
        // 编译表达式
        //将cached设置为true即可, 那么下次编译同一个表达式的时候将直接返回上一次编译的结果
        Expression compiledExp = AviatorEvaluator.compile(expression,true);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);  // false
    }

    @Test
    public void arrayAndCollection() {
        final List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add(" world");
        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        final Map<String, Date> map = new HashMap<String, Date>();
        map.put("date", new Date());
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("list", list);
        env.put("array", array);
        env.put("mmap", map);
        System.out.println(AviatorEvaluator.execute("list[0]+list[1]", env));   // hello world
        System.out.println(AviatorEvaluator.execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env));  // array[0]+array[1]+array[2]=4
        System.out.println(AviatorEvaluator.execute("'today is ' + mmap.date ", env));  // today is Wed Feb 24 17:31:45 CST 2016
        assertEquals("a", AviatorEvaluator.exec("string.split(s,',')[0]", "a,b,c,d"));
    }

    @Test
    public void ifelse() {
        System.out.println(AviatorEvaluator.exec("a>0? 'yes':'no'", 1));  // yes
    }

    /**
     * 分组捕获放入 env 是默认开启的，因此如果传入的 env 不是线程安全并且被并发使用，可能存在线程安全的隐患。关闭分组匹配，可以通过
     * AviatorEvaluator.setOption(Options.PUT_CAPTURING_GROUPS_INTO_ENV, false); 来关闭，对性能有稍许好处
     */
    @Test
    public void regex() {
//        String email = "killme*2009@gmail.com";
        String email = "killme_2009@gmail.com";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([\\w]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
        System.out.println(username); // killme2008
    }

    @Test
    public void nil() {
        System.out.println(AviatorEvaluator.execute("' nil == nil: ' + (nil == nil)"));   //true
        System.out.println(AviatorEvaluator.execute("'3>nil: '+ (3> nil)"));      //true
        System.out.println(AviatorEvaluator.execute("'true != nil: ' + (true!= nil)"));  //true
        System.out.println(AviatorEvaluator.execute("' >nil: ' + (' '>nil )"));    //true
        System.out.println(AviatorEvaluator.execute("'a==nil: '+ (a==nil) "));     //true, a 是 null
    }

    @Test
    public void date() {
        Map<String, Object> env = new HashMap<String, Object>();
        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env.put("date", date);
        env.put("dateStr", dateStr);
        Boolean result = (Boolean) AviatorEvaluator.execute("date==dateStr", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date > '2010-12-20 00:00:00:00' ", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date < '2200-12-20 00:00:00:00' ", env);
        System.out.println(result);  // true
        result = (Boolean) AviatorEvaluator.execute("date==date ", env);
        System.out.println(result);  // true
    }

    @Test
    public void bigInteger() {
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        System.out.println(AviatorEvaluator.exec("99999999999999999999999999999999 + 99999999999999999999999999999999"));
    }

    @Test
    public void calcBigInteger() {
        Object rt = AviatorEvaluator.exec("9223372036854775807100.356M * 2");
        System.out.println(rt + " " + rt.getClass());  // 18446744073709551614200.712 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("92233720368547758074+1000");
        System.out.println(rt + " " + rt.getClass());  // 92233720368547759074 class java.math.BigInteger
        BigInteger a = new BigInteger(String.valueOf(Long.MAX_VALUE) + String.valueOf(Long.MAX_VALUE));
        BigDecimal b = new BigDecimal("3.2");
        BigDecimal c = new BigDecimal("9999.99999");
        rt = AviatorEvaluator.exec("a+10000000000000000000", a);
        System.out.println(rt + " " + rt.getClass());  // 92233720368547758089223372036854775807 class java.math.BigInteger
        rt = AviatorEvaluator.exec("b+c*2", b, c);
        System.out.println(rt + " " + rt.getClass());  // 20003.19998 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("a*b/c", a, b, c);
        System.out.println(rt + " " + rt.getClass());  // 2.951479054745007313280155218459508E+34 class java.math.BigDecimal

    }


    @Test
    public void calcBigInteger1() {
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        Object rt = AviatorEvaluator.exec("9223372036854775807100.356 * 2");
        System.out.println(rt + " " + rt.getClass());  // 18446744073709551614200.712 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("92233720368547758074+1000");
        System.out.println(rt + " " + rt.getClass());  // 92233720368547759074 class java.math.BigInteger
        BigInteger a = new BigInteger(String.valueOf(Long.MAX_VALUE) + String.valueOf(Long.MAX_VALUE));
        BigDecimal b = new BigDecimal("3.2");
        BigDecimal c = new BigDecimal("9999.99999");
        rt = AviatorEvaluator.exec("a+10000000000000000000", a);
        System.out.println(rt + " " + rt.getClass());  // 92233720368547758089223372036854775807 class java.math.BigInteger
        rt = AviatorEvaluator.exec("b+c*2", b, c);
        System.out.println(rt + " " + rt.getClass());  // 20003.19998 class java.math.BigDecimal
        rt = AviatorEvaluator.exec("a*b/c", a, b, c);
        System.out.println(rt + " " + rt.getClass());  // 2.951479054745007313280155218459508E+34 class java.math.BigDecimal
        AviatorEvaluator.setOption(Options.MATH_CONTEXT, MathContext.DECIMAL64);
    }

    /**
     * seq.some(list, pred) 当集合中只要有一个元素满足谓词函数 pred 返回 true，立即返回 true，否则为 false。
     seq.every(list, pred) 当集合里的每个元素都满足谓词函数 pred 返回 true，则结果为 true，否则返回 false。
     seq.not_any(list, pred)，当集合里的每个元素都满足谓词函数 pred 返回 false，则结果为 true，否则返回 false。
     以及 seq.or(p1, p2, ...) 和 seq.and(p1, p2, ...) 用于组合 seq.gt、seq.lt 等谓词函数。
     */
    @Test
    public void seq() {
        Map<String, Object> env = new HashMap<String, Object>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(3);
        list.add(20);
        list.add(10);
        env.put("list", list);
        //求长度: count(list)
        Object result = AviatorEvaluator.execute("count(list)", env);
        System.out.println(result);  // 3
        //求和:
        //reduce函数接收三个参数,第一个是seq,第二个是聚合的函数,如+等,第三个是聚合的初始值
        result = AviatorEvaluator.execute("reduce(list,+,0)", env);
        System.out.println(result);  // 33
        //过滤出list中所有大于9的元素并返回集合;
        result = AviatorEvaluator.execute("filter(list,seq.gt(9))", env);
        System.out.println(result);  // [10, 20]
        //判断元素在不在集合里
        result = AviatorEvaluator.execute("include(list,10)", env);
        System.out.println(result);  // true
        //排序
        result = AviatorEvaluator.execute("sort(list)", env);
        System.out.println(result);  // [3, 10, 20]
        //遍历整个集合: map(list,println), map接受的第二个函数将作用于集合中的每个元素,这里简单地调用println打印每个元素
        AviatorEvaluator.execute("map(list,println)", env);
    }
}
