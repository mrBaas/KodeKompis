package net.tedes.kodekompis;

import java.util.List;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
	private Context _context;
	private List<DataBolk> bolkList;
 
    public ExpandableListAdapter(Context context, List<DataBolk> bolks) {
        this._context = context;
        this.bolkList = bolks;
    }
 
//    public void setData(List<DataBolk> data) {
//		clear();
//		if(data != null){
//			for(DataBolk appEntry : data) {
//				add(appEntry);
//			}
//		}
//	}
    
    public void addDataBolk(DataBolk bolk){
    	this.bolkList.add(bolk);
    }
    
    public List<DataBolk> getData() {
    	return this.bolkList;
    }
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	if(childPosition == 1) {
    		return this.bolkList.get(groupPosition).getmPassord();
    	} else {
    		return this.bolkList.get(groupPosition).getmBrukernavn();
    	}
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
 
        TextView listItemText = (TextView) convertView.findViewById(R.id.listitem_text);
        listItemText.setText(childText);
        
        ImageView listItemIcon = (ImageView) convertView.findViewById(R.id.listitem_icon);
        if(childPosition == 1) {
        	listItemIcon.setImageResource(Tedes.ICON_PASS);
        } else {
        	listItemIcon.setImageResource(Tedes.ICON_USER);
        }
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this.bolkList.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.bolkList.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = ((DataBolk)getGroup(groupPosition)).getmSted();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView listItemHeader = (TextView) convertView.findViewById(R.id.listitem_header);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        listItemHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}