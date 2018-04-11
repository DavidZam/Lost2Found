package es.lost2found.lost2foundUI.pickerUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import eltos.simpledialogfragment.color.SimpleColorWheelDialog;
import eltos.simpledialogfragment.list.AdvancedAdapter;
import eltos.simpledialogfragment.list.CustomListDialog;
import es.lost2found.R;

public class ColorPickerUI extends CustomListDialog<ColorPickerUI> implements SimpleColorWheelDialog.OnDialogResultListener {

    public static final String TAG = "ColorPickerUI.";

    public static final String
            COLOR = TAG + "color",
            COLORS = TAG + "colors";

    public static ColorPickerUI build(){
        return new ColorPickerUI();
    }

    public static final @ArrayRes
    int
            MATERIAL_COLOR_PALLET = R.array.material_pallet,
            MATERIAL_COLOR_PALLET_LIGHT = R.array.material_pallet_light,
            MATERIAL_COLOR_PALLET_DARK = R.array.material_pallet_dark,
            BEIGE_COLOR_PALLET = R.array.beige_pallet,
            COLORFUL_COLOR_PALLET = R.array.colorful_pallet;

    public static final int
            NONE = ColorView.NONE,
            AUTO = ColorView.AUTO;


    /**
     * Sets the colors to choose from
     * Default is the {@link ColorPickerUI#DEFAULT_COLORS} set
     *
     * @param colors array of rgb-colors
     */
    public ColorPickerUI colors(@ColorInt int[] colors){
        getArguments().putIntArray(COLORS, colors);
        return this;
    }

    /**
     * Sets the color pallet to choose from
     * May be one of {@link ColorPickerUI#MATERIAL_COLOR_PALLET},
     * {@link ColorPickerUI#MATERIAL_COLOR_PALLET_DARK},
     * {@link ColorPickerUI#MATERIAL_COLOR_PALLET_LIGHT},
     * {@link ColorPickerUI#BEIGE_COLOR_PALLET} or a custom pallet
     *
     * @param context a context to resolve the resource
     * @param colorArrayRes color array resource id
     */
    public ColorPickerUI colors(Context context, @ArrayRes int colorArrayRes){
        return colors(context.getResources().getIntArray(colorArrayRes));
    }

    /**
     * Sets the initially selected color
     *
     * @param color the selected color
     */
    public ColorPickerUI colorPreset(@ColorInt int color){
        getArguments().putInt(COLOR, color);
        return this;
    }

    /**
     * Set this to true to show a field with a color picker option
     *
     * @param allow allow custom picked color if true
     */
    public ColorPickerUI allowCustom(boolean allow){
        return setArg(CUSTOM, allow);
    }

    /**
     * Add a colored outline to the color fields.
     * {@link ColorPickerUI#NONE} disables the outline (default)
     * {@link ColorPickerUI#AUTO} uses a black or white outline depending on the brightness
     *
     * @param color color int or {@link ColorPickerUI#NONE} or {@link ColorPickerUI#AUTO}
     */
    /*public ColorPickerUI showOutline(@ColorInt int color){
        return setArg(OUTLINE, color);
    }*/

    protected static final @ColorInt int[] DEFAULT_COLORS = new int[]{
            0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
            0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
            0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
            0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722,
            0xff795548, 0xff9e9e9e, 0xff607d8b
    };

    protected static final int PICKER = 0x00BADA55;
    protected static final String CUSTOM = TAG + "custom";
    protected static final String PICKER_DIALOG_TAG = TAG + "picker";
    private static final String SELECTED = TAG + "selected";
    private static final String OUTLINE = TAG + "outline";

    private @ColorInt int mCustomColor = NONE;
    private @ColorInt int mSelectedColor = NONE;

    public ColorPickerUI(){
        grid();
        gridColumnWidth(R.dimen.dialog_color_item_size);
        choiceMode(SINGLE_CHOICE);
        choiceMin(1);
        colors(DEFAULT_COLORS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mCustomColor = savedInstanceState.getInt(CUSTOM, mCustomColor);
            mSelectedColor = savedInstanceState.getInt(SELECTED, mCustomColor);
        }
    }

    @Override
    protected AdvancedAdapter onCreateAdapter() {

        int[] colors = getArguments().getIntArray(COLORS);
        if (colors == null) colors = new int[0];
        boolean custom = getArguments().getBoolean(CUSTOM);

        // preset
        if (mSelectedColor == NONE && getArguments().containsKey(COLOR)){
            @ColorInt int preset = getArguments().getInt(COLOR, NONE);
            int index = indexOf(colors, preset);
            if (index < 0){ // custom preset
                mSelectedColor = mCustomColor = preset;
                if (custom){
                    choicePreset(colors.length);
                }
            } else {
                choicePreset(index);
                mSelectedColor = preset;
            }
        }

        // Selector provided by ColorView
        getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));

        return new ColorAdapter(colors, custom);
    }

    private int indexOf(int[] array, int item){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CUSTOM, mCustomColor);
        outState.putInt(SELECTED, mSelectedColor);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected boolean acceptsPositiveButtonPress() {
        if (getArguments().getInt(CHOICE_MODE) == SINGLE_CHOICE_DIRECT){
            return mSelectedColor != NONE;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id == PICKER){
            SimpleColorWheelDialog dialog = SimpleColorWheelDialog.build()
                    .theme(getTheme())
                    .alpha(false);
            if (mCustomColor != NONE){
                dialog.color(mCustomColor);
            } else if (mSelectedColor != NONE){
                dialog.color(mSelectedColor);
                mCustomColor = mSelectedColor;
            }
            dialog.show(this, PICKER_DIALOG_TAG);
            mSelectedColor = NONE;
            //onColorSet();
        } else {
            mSelectedColor = (int) id;
            //onColorSet();
        }
        onColorSet();
    }

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if (PICKER_DIALOG_TAG.equals(dialogTag) && which == BUTTON_POSITIVE){
            mSelectedColor = mCustomColor = extras.getInt(SimpleColorWheelDialog.COLOR);
            notifyDataSetChanged();
            onColorSet();
            if (getArguments().getInt(CHOICE_MODE) == SINGLE_CHOICE_DIRECT){
                pressPositiveButton();
                //onColorSet();
            }
            return true;
        }
        return false;
    }

    @Override
    protected Bundle onResult(int which) {
        Bundle b = super.onResult(which);
        int color = (int) b.getLong(SELECTED_SINGLE_ID);
        if (color == PICKER){
            b.putInt(COLOR, mCustomColor);
        } else {
            b.putInt(COLOR, color);
        }

        long[] ids = b.getLongArray(SELECTED_IDS);
        if (ids != null) {
            int[] colors = new int[ids.length];
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == PICKER) {
                    colors[i] = mCustomColor;
                } else {
                    colors[i] = (int) ids[i];
                }
            }
            b.putIntArray(COLORS, colors);
        }
        return b;
    }


    protected class ColorAdapter extends AdvancedAdapter<Integer>{

        public ColorAdapter(int[] colors, boolean addCustomField){
            if (colors == null) colors = new int[0];

            Integer[] cs = new Integer[colors.length + (addCustomField ? 1 : 0)];
            for (int i = 0; i < colors.length; i++) {
                cs[i] = colors[i];
            }
            if (addCustomField){
                cs[cs.length-1] = PICKER;
            }
            setData(cs, new ItemIdentifier<Integer>(){
                @Nullable
                public Long getIdForItem(Integer color) {
                    return Long.valueOf(color);
                }
            });
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ColorView item;

            if (convertView instanceof ColorView){
                item = (ColorView) convertView;
            } else {
                item = new ColorView(getContext());
            }

            int color = getItem(position);

            if ( color == PICKER){
                item.setColor(mCustomColor);
                item.setStyle(ColorView.Style.PALETTE);
            } else {
                item.setColor(getItem(position));
                item.setStyle(ColorView.Style.CHECK);
            }

            @ColorInt int outline = getArguments().getInt(OUTLINE, ColorView.NONE);
            if (outline != NONE){
                float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        2 /*dp*/, getResources().getDisplayMetrics());
                item.setOutlineWidth((int) width);
                item.setOutlineColor(outline);
            }

            return super.getView(position, item, parent);
        }
    }

    public void onColorSet() {
        // Do something with the color chosen by the user

        //Button colorBtn = getActivity().findViewById(R.id.color_button);

        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("colorBtn", 0);
        String colorhex = String.valueOf(mSelectedColor);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("colorChoice", colorhex);
        ed.apply();

        /*EditText color = getActivity().findViewById(R.id.color_show);
        String colorhex = String.valueOf(mSelectedColor);
        color.setText(colorhex);
        color.setTextSize(15);*/
    }
}