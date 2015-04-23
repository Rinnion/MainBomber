package com.me.fsm;

public class Main {

    public static void main(String[] args) {
        MyState stateLogo = new MyState("logo", "I'm logo");
        MyState stateMenu = new MyState("menu", "I'm menu");
        MyState stateGame = new MyState("game", "I'm game");
        MyState stateExit = new MyState("exit", "I'm exit");

        Input inputClose = new InputImpl("close");
        Input inputStart = new InputImpl("start");

        StateEngine<MyState> stateEngine = StateEngineFactory.create();
        stateEngine.add(stateLogo, inputClose, null, stateMenu);
        stateEngine.add(stateMenu, inputStart, null, stateGame);
        stateEngine.add(stateGame, inputClose, null, stateMenu);
        stateEngine.add(stateMenu, inputClose, null, stateExit);
        stateEngine.done();

        FSM<MyState> fsm = stateEngine.makeFSM(stateLogo);

        System.out.println(fsm.getState().SayHello());
        fsm.doIt(inputClose);
        System.out.println(fsm.getState().SayHello());
        fsm.doIt(inputClose);
        System.out.println(fsm.getState().SayHello());
    }
}

class MyState extends StateImpl {

    private String hello;

    public MyState(String name, String hello) {
        super(name);
        this.hello = hello;
    }

    public String SayHello() {
        return hello;
    }
}