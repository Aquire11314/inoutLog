package view;

import base.BaseDAO;
import bean.Goods;
import bean.GoodsLog;
import constants.AppConstants;
import constants.DAO;
import dao.GoodsDAO;
import dao.GoodsLogDAO;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * 库存信息视图
 */
public class GoodsLogView extends JFrame {

    private static final long serialVersionUID = 5870864087464173884L;

    private final int maxPageNum = 99;

    private JPanel jPanelNorth, jPanelSouth, jPanelCenter;
    private JButton jButtonFirst, jButtonLast, jButtonNext, jButtonPre, jButtonAdd, jButtonDelete;
    private JLabel currPageNumJLabel;
    public static JTable jTable;
    private JScrollPane jScrollPane;
    private DefaultTableModel myTableModel;

    private static  Goods goods;

    public static String[] column = { AppConstants.GOODSLog_INDEX,AppConstants.GOODSLog_NAME,AppConstants.GOODSLog_NUM,AppConstants.GOODSLog_CREATEDATE,AppConstants.GOODSLog_DESC};
    public static int currPageNum = 1;

    public GoodsLogView(Goods goods) {
        this.goods = goods;
        init();
    }

    private void init() {
        setTitle(AppConstants.GOODSLOGVIEW_TITLE);

        // north panel
        jPanelNorth = new JPanel();
        jPanelNorth.setLayout(new GridLayout(1, 4));
        jPanelNorth.add(new Label());
        // add
        jButtonAdd = new JButton(AppConstants.PARAM_ADD);
        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddGoodsLogView(goods.getId());
            }
        });
        jPanelNorth.add(jButtonAdd);
        // delete
        jButtonDelete = new JButton(AppConstants.PARAM_DELETE);
        jButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( jTable.getSelectedRow()>=0){
                    final JLabel label = new JLabel();
                    int result = JOptionPane.showConfirmDialog(jPanelNorth,"确定要删除?", "警告",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        String id = jTable.getValueAt(jTable.getSelectedRow(),0).toString();
                        delete(new Integer(id));
                    }
                }
            }
        });
        jPanelNorth.add(jButtonDelete);
        jPanelNorth.add(new Label());


        // center panel
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(1, 1));

        // init jTable
        String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),1);
        System.out.println(Arrays.toString(result[0]));
        myTableModel = new DefaultTableModel(result, column){
            public boolean isCellEditable(int row, int column)
            {
                return false;//单元格不可编辑
            }
        };

        jTable = new JTable(myTableModel);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, cr);
        initJTable(jTable, result);

        jScrollPane = new JScrollPane(jTable);
        jPanelCenter.add(jScrollPane);

        // south panel
        jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(1, 5));

        jButtonFirst = new JButton(AppConstants.MAINVIEW_FIRST);
        jButtonFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = 1;
                String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        jButtonPre = new JButton(AppConstants.MAINVIEW_PRE);
        jButtonPre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum--;
                if (currPageNum <= 0) {
                    currPageNum = 1;
                }
                String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        jButtonNext = new JButton(AppConstants.MAINVIEW_NEXT);
        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum++;
                if (currPageNum > maxPageNum) {
                    currPageNum = maxPageNum;
                }
                String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });
        jButtonLast = new JButton(AppConstants.MAINVIEW_LAST);
        jButtonLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currPageNum = maxPageNum;
                String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),currPageNum);
                initJTable(jTable, result);
                currPageNumJLabel.setText(AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum
                        + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
            }
        });

        currPageNumJLabel = new JLabel(
                AppConstants.MAINVIEW_PAGENUM_JLABEL_DI + currPageNum + AppConstants.MAINVIEW_PAGENUM_JLABEL_YE);
        currPageNumJLabel.setHorizontalAlignment(JLabel.CENTER);

        jPanelSouth.add(jButtonFirst);
        jPanelSouth.add(jButtonPre);
        jPanelSouth.add(currPageNumJLabel);
        jPanelSouth.add(jButtonNext);
        jPanelSouth.add(jButtonLast);

        this.add(jPanelNorth, BorderLayout.NORTH);
        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(jPanelSouth, BorderLayout.SOUTH);

        setBounds(400, 200, 550, 240);
//		setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void initJTable(JTable jTable, String[][] result) {
        for(int i=0; result!=null&&i<result.length;i++){
            result[i][1]=goods.getName();
        }
        ((DefaultTableModel) jTable.getModel()).setDataVector(result, column);
        // 标题居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        jTable.getTableHeader().setDefaultRenderer(hr);
        //只能选中一行
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setRowHeight(20);
    }


    private void delete(Integer id) {
        try {
            ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "删除失败,请刷新再操作！","",JOptionPane.ERROR_MESSAGE);
        }
        String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).selectByGoodsId(goods.getId(),1);
        initJTable(GoodsLogView.jTable, result);
        GoodsView.initFirst();
    }
}
