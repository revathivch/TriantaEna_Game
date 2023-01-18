package Games;

public class Piece {
    protected String name;

    public Piece(String name) {
        this.set_name(name);
    }
    public void set_name(String name){
        this.name = name;
    }

    public String get_name(){
        return this.name;
    }
}


