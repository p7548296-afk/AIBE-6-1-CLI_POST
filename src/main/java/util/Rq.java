package util;

public class Rq {
    private String actionPath;
    private int id;

    public Rq(String command) {
        String[] bits = command.split(" ");
        this.actionPath = bits[0];

        if (bits.length > 1) {
            try {
                this.id = Integer.parseInt(bits[1]);
            } catch (NumberFormatException e) {
                this.id = 0;
            }
        }
    }

    public String getActionPath() { return actionPath; }
    public int getId() { return id; }
}
