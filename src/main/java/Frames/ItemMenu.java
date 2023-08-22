/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frames;

import com.mycompany.analizadorlexico.Token;
import javax.swing.Icon;
import javax.swing.JMenuItem;


        
public class ItemMenu extends JMenuItem{
    
    private Token token; 
    
    public ItemMenu(Token token, String text){
        this.token = token;
        this.setText(text);
    }

    

    public Token getToken() {
        return token;
    }
    
    
}
