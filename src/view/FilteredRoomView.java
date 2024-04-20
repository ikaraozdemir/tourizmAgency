package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FilteredRoomView extends Layout{
    private JPanel container;
    private JTable tbl_searched_room;
    private JScrollPane scrol;
    private Object[] col_searched_room;
    private final DefaultTableModel tmbl_searched_room = new DefaultTableModel();

    public FilteredRoomView () {
        this.add(container);
        this.guiInitialize(700, 700);

    }

    public void loadSearchedRoomTable(ArrayList<Object[]> roomReservationRow) {
        this.col_searched_room = new Object[]{"ID", "Otel","Otel ID", "Sezon Başlangıcı", "Sezon Bitişi", "Pansiyon Tipi",
                "Oda Stoğu", "Oda Tipi", "Oda Özellikleri", "Toplam Gün", "Yetişkin İçin Fiyat", "Çocuk İçin Fiyat", "Toplam Fiyat"};
        createTable(this.tmbl_searched_room, this.tbl_searched_room, col_searched_room, roomReservationRow);
    }
}
