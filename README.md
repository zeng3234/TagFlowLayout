# TagFlowLayout
Android tagView ,可单选，多选

**==注==**：部分参考鸿洋的FlowLayout， demo中的测数据，比较懒，所以直接copy 鸿洋的demo....


#### 功能：
单选、多选、限制多选、单击、以及选中事件回调、每行最后一个填满、默认TagItemView、adapter形式填充数据、builder构建
事件：

```
    public interface OnSelectListener {
        void onSelected(View view, int position);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position);
    }
```


#### 效果图
![image](https://note.youdao.com/yws/api/personal/file/CBBA3E6C896642528D804795CAE328D3?method=download&shareKey=4f32ae0b0cf095dede713a74629f1c81)






####  使用方法

暂时未弄jar or maven, 下载用吧~~


默认使用方式

```
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_fragment, null);
        mFlowLayout = view.findViewById(R.id.flowlayout);
        mFlowLayout.setChoiceMode(TagFlowLayout.CHOICE_MODE_SINGLE);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(int position, String str) {
                return buildTagView(str);
            }
        });
        return view;
    }

    private TextView buildTagView(String pstr) {
        TextView tv = new TextView(getActivity());
        tv.setText(pstr);
        tv.setBackgroundResource(R.drawable.tag_test_item_bg);
        ViewGroup.MarginLayoutParams lps = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        lps.setMargins(5, 5, 5, 5);
        tv.setPadding(15, 15, 15, 15);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lps);
        return tv;
    }
```


使用默认tag

```
        mFlowLayout = view.findViewById(R.id.flowlayout);
        mFlowLayout.setChoiceMode(TagFlowLayout.CHOICE_MODE_SINGLE);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(int position, String str) {
                return buildTagView(str);
            }
        });

    private TextView buildTagView(String pstr) {
        TagItemView.Builder builder = new TagItemView.Builder(getContext());
        builder.wh(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//宽高，可不设置。默认两个wrap_content
        builder.margin(5, 5, 5, 5);
        builder.padding(35, 10, 35, 10);
        builder.radius(15); //TYPE_CIRCLE时，该参数无效，强制改成高度的一半
        builder.bg(TagItemView.Builder.TYPE_CIRCLE, Color.WHITE, 2, Color.GREEN);
        builder.bgSelected(Color.WHITE, Color.RED);
        builder.lastFixWidth(true); //最后一个item,拉伸填满一行
        builder.textColorSelector(Color.GRAY, Color.RED);
        TagItemView itemView = builder.build();
        itemView.setText(pstr);
        return itemView;
    }
```

更多使用参考demo,

