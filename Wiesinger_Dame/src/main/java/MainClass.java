import processing.core.PApplet;

public class MainClass extends PApplet {

    int leftMargin = 20;
    int upperMargin = 20;
    int boxlength = 80;
    int selectedDame = -1;
    Dame[] damenarray = new Dame[24];
    boolean start = true;

    public static void main(String[] args) {
        PApplet.main("MainClass", args);
    }
    public void settings() {
        size(800, 800);

    }
    public void setup() {
        frameRate(60);

    }
    public void draw() {

        if(start){
            damenarray = reset();
            start = false;
        }

        int changecolor = 0;
        boolean inversed = false;
        boolean color;
        background(200);


        //region Brett
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                color = boardColor(inversed, changecolor);
                changecolor++;

                if(boardColor(inversed, changecolor)){
                    fill(193, 148, 93);

                }else{
                    fill(255, 211, 158);
                }

                stroke(193, 148, 93);
                rect(leftMargin + j * boxlength, upperMargin + i * boxlength, boxlength, boxlength);
            }
            if(inversed){
                inversed = false;
            }else{
                inversed = true;
            }

        }
        //endregion

        int hoveredIndex = isMouseOverDame(damenarray, mouseX, mouseY);

        //region Hover-Effekt
        if(hoveredIndex != -1){
            if(mousePressed){
                if(selectedDame == hoveredIndex){
                    selectedDame = -1;
                }else{
                    selectedDame = hoveredIndex;
                }
                delay(100);

            }else {
                fill(255, 231, 204);
            }
            rect(damenarray[hoveredIndex].X -boxlength/2, damenarray[hoveredIndex].Y - boxlength/2, boxlength, boxlength);
        }


        if(selectedDame != -1){
            fill(255);
            rect(damenarray[selectedDame].X -boxlength/2, damenarray[selectedDame].Y - boxlength/2, boxlength, boxlength);
        }
        //endregion

        drawSuggestion(damenarray, selectedDame);

        //region Damen zeichnen
        stroke(0);
        fill(50);
        for (int i = 12; i < 24; i++) {
            ellipse(damenarray[i].X, damenarray[i].Y, boxlength /5 * 4, boxlength /5 * 4);
        }

        fill(245);
        for (int i = 0; i < 12; i++) {
            ellipse(damenarray[i].X, damenarray[i].Y, boxlength /5 * 4, boxlength /5 * 4);
        }
        //endregion

        //region untere Box und Restet Button
        fill(255, 211, 158);
        rect(leftMargin, upperMargin*2 + 8*boxlength, 8*boxlength, 25);
        textSize(boxlength/5);
        fill(0);
        text("Weiß ist an der Reihe", leftMargin + 5, upperMargin*2 + 8*boxlength + boxlength/5);

        if(mouseoverBox(mouseX, mouseY) && mousePressed){
            fill(193, 148, 93);
            damenarray = reset();
        }else if(mouseoverBox(mouseX, mouseY)){
            fill(255, 231, 204);
        }else {
            fill(255, 211, 158);
        }
        rect(leftMargin + 2* boxlength, upperMargin*2 + 8*boxlength + 30, 4*boxlength, 25);
        fill(0);

        textSize(boxlength/5);
        text("neues Spiel starten", leftMargin + 3* boxlength, upperMargin*2 + 8*boxlength + 50);
        fill(255);
        //endregion
    }

    public Dame[] reset(){

        Dame[] array = new Dame[24];

        int j = 0;
        for (int i = 0; i < 4; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength/2);
            j = j + 2;
        }
        j = 1;
        for (int i = 4; i < 8; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength + boxlength/2);
            j = j + 2;
        }
        j = 0;
        for (int i = 8; i < 12; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength/2 + 2 * boxlength);
            j = j + 2;
        }
        j = 1;
        for (int i = 12; i < 16; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength/2 + 5 * boxlength);
            j = j + 2;
        }
        j = 0;
        for (int i = 16; i < 20; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength/2 + 6 * boxlength);
            j = j + 2;
        }
        j = 1;
        for (int i = 20; i < 24; i++) {
            array[i] = new Dame(leftMargin + j * boxlength + boxlength/2, upperMargin + boxlength/2 + 7 * boxlength);
            j = j + 2;
        }


        return array;
    }

    public Boolean boardColor(Boolean inversed, int changecolor){

        Boolean color = false;

        if(!inversed){
            if(changecolor%2 == 0){
                color = true;
            }else{
                color = false;
            }
        }else{
            if(changecolor%2 == 0){
                color = false;
            }else{
                color = true;
            }
        }

        return color;
    }

    public Boolean mouseoverBox(int mouseX, int mouseY){

        boolean isItHovering = false;

        if(mouseX >= leftMargin + 2* boxlength && mouseX <= leftMargin + 6* boxlength){
            isItHovering = true;
        }

        if(isItHovering && mouseY >= upperMargin*2 + 8*boxlength + 30 && mouseY <= upperMargin*2 + 8*boxlength + 50){
            isItHovering = true;
        }else{
            isItHovering = false;
        }

        return isItHovering;
    }

    public int isMouseOverDame(Dame[] damenarray, int mouseX, int mouseY){

        for (int i = 0; i < damenarray.length; i++) {
            if(     mouseX > damenarray[i].X - boxlength/2 &&
                    mouseX < damenarray[i].X + boxlength/2 &&
                    mouseY > damenarray[i].Y - boxlength/2 &&
                    mouseY < damenarray[i].Y + boxlength/2
            ){
                return i;
            }
        }

        return -1;
    }

    public void drawSuggestion(Dame[] damenarray, int selectedDame){

        fill(255, 231, 204);
        if(selectedDame != -1) {

            if(selectedDame < 12){

                Boolean isOccupied = false;

                for (int i = 0; i < damenarray.length; i++) {
                    if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength){
                        isOccupied = true;
                    }
                }
                if(!isOccupied){
                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                }
                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
            }else{
                rect(damenarray[selectedDame].X - boxlength*1.5f, damenarray[selectedDame].Y - boxlength* 1.5f, boxlength, boxlength);
                rect(damenarray[selectedDame].X + boxlength*0.5f, damenarray[selectedDame].Y - boxlength* 1.5f, boxlength, boxlength);
            }
        }
    }
}
