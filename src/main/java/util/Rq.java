package util;

public class Rq {
    private String actionPath;
    private int id;

    public Rq(String command) {
        String[] bits = command.trim().split("\\s+");

        if (bits.length == 0 || bits[0].isEmpty()) {
            this.actionPath = "";
            this.id = 0;
            return;
        }

        this.actionPath = bits[0];

        if (bits.length > 1) {
            try {
                this.id = Integer.parseInt(bits[1]);
            } catch (NumberFormatException e) {
                // 숫자가 아니거나 int 범위를 벗어날 경우 0으로 처리
                this.id = 0;
            }
        }
    }

    public String getActionPath() { return actionPath; }
    public int getId() { return id; }
}