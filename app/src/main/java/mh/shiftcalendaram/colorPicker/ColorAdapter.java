package mh.shiftcalendaram.colorPicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import mh.shiftcalendaram.R;

public class ColorAdapter extends BaseAdapter {

	
	Context context;
	
	
	public ColorAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(75,90));
        } else {
            imageView = (ImageView) convertView;
        }
		
		imageView.setImageResource(pictures[position].getImage());
	
		return imageView;
		
	

	}
	
	private ColorSelected[] pictures = {
			new ColorSelected(R.drawable.color_circle_dark_red, "#9b1c20"),
			new ColorSelected(R.drawable.color_circle_pink, "#d8355b"),
		    new ColorSelected(R.drawable.color_circle_orange, "#ffb369"), 
			new ColorSelected(R.drawable.color_circle_dark_yellow, "#D7DF01"),
			new ColorSelected(R.drawable.color_circle_azure, "#1abc9c"),
			new ColorSelected(R.drawable.color_circle_violet, "#9b59b6"), 
			new ColorSelected(R.drawable.color_circle_light_blue, "#3498db"),
			new ColorSelected(R.drawable.color_circle_green, "#aaec99"),
			new ColorSelected(R.drawable.color_circle_light_green, "#99cc00"), 
			new ColorSelected(R.drawable.color_circle_darkgreen, "#157718"),
			new ColorSelected(R.drawable.color_circle_red, "#ff4d4d"), 
			new ColorSelected(R.drawable.color_circle_gray, "#767676")};

	
	public ColorSelected getPictures(int position)
	{
		return pictures[position];
	}
}


