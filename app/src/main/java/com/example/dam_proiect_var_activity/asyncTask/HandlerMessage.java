package com.example.dam_proiect_var_activity.asyncTask;

public class HandlerMessage<R> implements Runnable {
    //este trimis RunnableTask dupa ce a fost primit din firul principal (activitate/fragment)
    private final Callback<R> mainThreadOperation;
    //este trimis din RunnableTask
    private final R result;

    public HandlerMessage(Callback<R> mainThreadOperation, R result) {
        this.mainThreadOperation = mainThreadOperation;
        this.result = result;
    }

    @Override
    public void run() {
        //se trimite rezultatul in activitate/fragment
        mainThreadOperation.runResultOnUiThread(result);
    }
}
