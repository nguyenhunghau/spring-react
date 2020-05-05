package com.example.management.dto;

import java.util.List;

/**
 *
 * @author Le Tan Phuc
 * @email letanphuc@fabercompany.co.jp
 */
public class MenuDTO {
    
    private String text;
    private String link;
    private String icon;
    private List<MenuDTO> childMenu;

    public MenuDTO(String text, String link, String icon, List<MenuDTO> childMenu) {
        this.text = text;
        this.link = link;
        this.icon = icon;
        this.childMenu = childMenu;
    }
    
    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuDTO> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuDTO> childMenu) {
        this.childMenu = childMenu;
    }
    
}
