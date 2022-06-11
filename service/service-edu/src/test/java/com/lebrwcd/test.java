package com.lebrwcd;/**
 * @author lebrwcd
 * @date 2022/4/24
 * @note
 */


import java.sql.Statement;

/**
 * ClassName test
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/4/24
 */
public class test {
    public static void main(String[] args) {

        Context context = new Context();

        System.out.println(context.getState());   //A

        context.Request();  //2
        context.Request();  //1
        context.Request();  //0

        System.out.println(context.getState());  //B

        context.Request();//补货
        System.out.println(context.getState());  //A
        System.out.println(context.getCount());  //5

    }

}

//贩卖机
class Context{

    private int count;

    private State state;

    public Context(){

        //初始饮料数量为3
        count = 3;
        state = new StateA();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    //购买饮料请求
    public void Request(){
        state.Handle(this);
    }
}

//状态
interface State{
    public void Handle(Context context);
}
//有货状态
class StateA implements State{

    @Override
    public void Handle(Context context) {

        int count = context.getCount();
        if(count >= 1){
            System.out.println("购买成功");
            context.setCount(count-1);
            if(context.getCount() == 0){
                //状态从有货到无货
                context.setState(new StateB());
            }
        }
    }
}
//无货状态
class StateB implements State{

    @Override
    public void Handle(Context context) {

        int count = context.getCount();

        if(count == 0){
            System.out.println("购买失败，等待补货");

            //补货
            context.setCount(5);
            System.out.println("补货成功，请重新购买");

            //无货->有货
            context.setState(new StateA());
        }

    }
}






