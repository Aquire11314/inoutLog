/**
 * 项目名：student
 * 修改历史：
 * 作者： MZ
 * 创建时间： 2016年1月7日-上午11:07:57
 */
package view;

import base.BaseDAO;
import bean.Goods;
import constants.AppConstants;
import constants.DAO;
import dao.GoodsDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 模块说明：
 * 
 */
public class UpdateGoodsView extends JFrame {

	private static final long serialVersionUID = 5292738820127102731L;

	private JPanel jPanelCenter, jPanelSouth;
	private JButton updateButton, exitButton;
	private JTextField name, desc;
	private Goods goods;

	public UpdateGoodsView(Goods goods) {
		this.goods = goods;
		init();
	}

	private void init() {
		setTitle(AppConstants.UPDATEVIEW_TITLE);
		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(4, 4));
		jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());
		jPanelCenter.add(new JLabel());

		jPanelCenter.add(new JLabel());
		jPanelCenter.add(new JLabel(AppConstants.GOODS_NAME,JLabel.CENTER));
		name = new JTextField(goods.getName(),1);
		jPanelCenter.add(name);
		jPanelCenter.add(new JLabel());
		jPanelCenter.add(new JLabel());
		jPanelCenter.add(new JLabel(AppConstants.GOODS_DESC,JLabel.CENTER));
		desc = new JTextField(goods.getDesc());
		jPanelCenter.add(desc);
		jPanelCenter.add(new JLabel());

		jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());jPanelCenter.add(new JLabel());
		jPanelCenter.add(new JLabel());
		// south panel
		jPanelSouth = new JPanel();
		jPanelSouth.setLayout(new GridLayout(1, 2));
		updateButton = new JButton(AppConstants.UPDATEVIEW_TITLE);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.getText()!=null&&name.getText().trim().length()>0) {
					goods.setName(name.getText());
					goods.setDesc(desc.getText());
					boolean isSuccess = false;
					try {
						isSuccess = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).update(goods);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "物料名称不能重复","",JOptionPane.ERROR_MESSAGE);
					}
					if (isSuccess) {
						dispose();
						if (GoodsView.currPageNum < 0 || GoodsView.currPageNum > 99) {
							GoodsView.currPageNum = 1;
						}
						String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO))
								.selectLikeName(null,GoodsView.currPageNum);
						GoodsView.initJTable(GoodsView.jTable, result);
					}
				}
			}
		});
		jPanelSouth.add(updateButton);
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
		name.setText("");
		desc.setText("");
	}
}
