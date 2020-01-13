package guiao1;

public class Counter {

    private int inc;

    public Counter(){
        this.inc=0;
    }

    public int increment(){
        return ++this.inc;
    }

    public int getInc(){
        return this.inc;
    }

}
