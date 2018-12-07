package jogo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public interface Player {
    void mexer();
    void atira();
    void keyPressed(KeyEvent tecla);
    void keyReleased(KeyEvent tecla);
    Image getImagem();
    Rectangle getBounds();
    boolean isVisivel();
    void setVisivel(boolean visible);
    List<Missel> getMisseis();
    int getX();
    int getY();
    void setEmModoEspecial(Boolean emModoEspecial);
    int getAltura();
    int getLargura();
}
