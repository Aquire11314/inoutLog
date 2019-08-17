package view;

import base.BaseDAO;
import bean.GoodsLog;
import constants.AppConstants;
import constants.DAO;
import dao.GoodsLogDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddGoodsLogView extends JFrame {

    private static final long serialVersionUID = -1984182788841566838L;

    private JPanel jPanelCenter, jPanelSouth;
    private JButton addButton, exitButton;
    private JTextField num, desc;
    private Integer goodsId;

    public AddGoodsLogView(Integer goodsId) {
        this.goodsId =goodsId;
        init();
    }

    private void init() {
        setTitle(AppConstants.ADDVIEW_TITLE);
        // center panel
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridLayout(4, 4));
        jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());
        jPanelCenter.add(new JLabel());

        jPanelCenter.add(new JLabel());
        jPanelCenter.add(new JLabel(AppConstants.GOODS_NUM,JLabel.CENTER));
        num = new JTextField();
        jPanelCenter.add(num);
        jPanelCenter.add(new JLabel());
        jPanelCenter.add(new JLabel());
        jPanelCenter.add(new JLabel(AppConstants.GOODS_DESC,JLabel.CENTER));
        desc = new JTextField();
        jPanelCenter.add(desc);
        jPanelCenter.add(new JLabel());

        jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());
        jPanelCenter.add(new JLabel());
        // south panel
        jPanelSouth = new JPanel();
        jPanelSouth.setLayout(new GridLayout(1, 2));
        addButton = new JButton(AppConstants.ADDVIEW_ADDBUTTON);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new Integer(num.getText())!=0) {
                    GoodsLog goodsLog = new GoodsLog();
                    goodsLog.setCreateDate(new Date());
                    goodsLog.setGoodsId(goodsId);
                    goodsLog.setNum(new Integer(num.getText()));
                    goodsLog.setDesc(desc.getText());
                    boolean isSuccess = false;
                    try {
                       isSuccess = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO)).insert(goodsLog);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "添加进出库异常","",JOptionPane.ERROR_MESSAGE);
                    }
                    if (isSuccess) {
                        setEmpty();
                        if (GoodsLogView.currPageNum < 0 || GoodsLogView.currPageNum > 99) {
                            GoodsLogView.currPageNum = 1;
                        }
                        String[][] result = ((GoodsLogDAO) BaseDAO.getAbilityDAO(DAO.GoodsLogDAO))
                                .selectByGoodsId(goodsId,GoodsLogView.currPageNum);
                        GoodsLogView.initJTable(GoodsLogView.jTable, result);
                        GoodsView.initFirst();
                    }
                }
            }
        });
        jPanelSouth.add(addButton);
        exitButton = new JButton(AppConstants.EXITBUTTON);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jPanelSouth.add(exitButton);

        this.add(jPanelCenter, BorderLayout.CENTER);
        this.add(jPanelSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(470, 200, 400, 270);
        setResizable(false);
        setVisible(true);
    }

    private void setEmpty() {
        num.setText("");
        desc.setText("");
    }
}
