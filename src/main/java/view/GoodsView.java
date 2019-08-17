/**
 * 项目名：student
 * 修改历史：
 * 作者： MZ
 * 创建时间： 2016年1月6日-下午1:37:39
 */
package view;

import base.BaseDAO;
import bean.Goods;
import constants.AppConstants;
import constants.DAO;
import dao.GoodsDAO;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * 模块说明： 首页
 *
 */
public class GoodsView extends JFrame {

	private static final long serialVersionUID = 5870864087464173884L;

	private final int maxPageNum = 99;

	private JPanel jPanelNorth, jPanelSouth, jPanelCenter;
	private JButton jButtonFirst, jButtonLast, jButtonNext, jButtonPre, jButtonAdd, jButtonDelete, jButtonUpdate,
			jButtonFind;
	private JLabel currPageNumJLabel;
	private JTextField condition;
	public static JTable jTable;
	private JScrollPane jScrollPane;
	private DefaultTableModel myTableModel;

	public static String[] column = { AppConstants.GOODS_INDEX,AppConstants.GOODS_NAME,AppConstants.GOODS_DESC,AppConstants.GOODS_STORE};
	public static int currPageNum = 1;

	public GoodsView() {
		init();
	}

	private void init() {
		setTitle(AppConstants.MAINVIEW_TITLE);

		// north panel
		jPanelNorth = new JPanel();
		jPanelNorth.setLayout(new GridLayout(1, 5));
		condition = new JTextField(AppConstants.PARAM_FIND_CONDITION);
		condition.addKeyListener(new FindListener());
		jPanelNorth.add(condition);
		// query by name
		jButtonFind = new JButton(AppConstants.PARAM_FIND);
		jButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				find();
			}
		});
		jButtonFind.addKeyListener(new FindListener());
		// add
		jPanelNorth.add(jButtonFind);
		jButtonAdd = new JButton(AppConstants.PARAM_ADD);
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddGoodsView();
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
		// update
		jButtonUpdate = new JButton(AppConstants.PARAM_UPDATE);
		jButtonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=jTable.getSelectedRow();
				if(row<0){
					JOptionPane.showMessageDialog(null, "请选择一行记录！","",JOptionPane.PLAIN_MESSAGE);
					return ;
				}
				System.out.println("row="+row);
				Integer id=  new Integer(jTable.getValueAt(row,0).toString());
				String name = (String) jTable.getValueAt(row,1);
				String desc = (String) jTable.getValueAt(row,2);
				Integer sum = new Integer(jTable.getValueAt(row,3).toString());
				new UpdateGoodsView(new Goods(id,name,desc,sum));
			}
		});
		jPanelNorth.add(jButtonUpdate);

		// center panel
		jPanelCenter = new JPanel();
		jPanelCenter.setLayout(new GridLayout(1, 1));

		// init jTable
		String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,currPageNum);
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
				String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,currPageNum);
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
				String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,currPageNum);
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
				String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,currPageNum);
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
				String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,currPageNum);
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

		setBounds(400, 200, 750, 340);
//		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		//table 行双击事件
		jTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){//点击几次，这里是双击事件
					int row=jTable.getSelectedRow();
					if(row<0){
						JOptionPane.showMessageDialog(null, "请选择一行记录！","",JOptionPane.PLAIN_MESSAGE);
						return ;
					}
					Integer id=  new Integer(jTable.getValueAt(row,0).toString());
					String name = (String) jTable.getValueAt(row,1);
					String desc = (String) jTable.getValueAt(row,2);
					Integer sum = new Integer(jTable.getValueAt(row,3).toString());
					new GoodsLogView(new Goods(id,name,desc,sum));
				}
			}
		});
	}

	public static void initJTable(JTable jTable, String[][] result) {
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

	private class FindListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				find();
			}
		}
	}

	public static void initFirst(){
		String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,1);
		initJTable(GoodsView.jTable, result);
	}
	private void find() {
		currPageNum = 0;
		String param = condition.getText();
		if ("".equals(param) || param == null) {
			initJTable(GoodsView.jTable, null);
			currPageNumJLabel.setText(AppConstants.MAINVIEW_FIND_JLABEL);
			return;
		}
		String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(param,null);
		condition.setText("");
		initJTable(GoodsView.jTable, result);
		currPageNumJLabel.setText(AppConstants.MAINVIEW_FIND_JLABEL);
	}

	private void delete(Integer id) {
		try {
			((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "删除失败,请刷新再操作！","",JOptionPane.ERROR_MESSAGE);
		}
		String[][] result = ((GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO)).selectLikeName(null,0);
		condition.setText("");
		initJTable(GoodsView.jTable, result);
	}
}
