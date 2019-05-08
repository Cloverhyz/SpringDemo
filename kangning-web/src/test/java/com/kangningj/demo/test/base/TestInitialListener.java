package com.kangningj.demo.test.base;



import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;


/**
 * 测试启动监听类,用于测试环境初始化
 */
public class TestInitialListener implements TestExecutionListener {

    public static Boolean lockInitial = false;

    @Override
    public  void beforeTestClass(TestContext testContext) throws Exception {
        synchronized(lockInitial){
                //如果已经初始化过了，不再初始化
               if(lockInitial){
                   return ;
               }
        }

    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
//            System.out.print("prepareTestInstance");
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        synchronized(lockInitial){
            //如果已经初始化过了，不再初始化
            if(lockInitial){
                lockInitial= !lockInitial ;
            }
        }
    }
}
