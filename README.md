# EasyLoopViewPager
A Easy Loop ViewPager Adapter for Android

实现原理是在头尾各加一个item。

#使用
1.继承EasyLoopViewPagerAdapter

    public class MyAdapter<T> extends EasyLoopViewPagerAdapter{

      public MyAdapter(Context context, int layoutId) {
          super(context, layoutId);
      }
      
      public void convert(View view, T item, int position){
        //TODO 在这里实现adapter布局
        //	TextView textview = view.findViewById(R.id.item_text);
        //	textview.setText(item.getName);
      }
      
    }
    
2.设置ViewPager

    public class MyActivity extends AppCompatActivity {

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.my_activity);
            
            ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
            MyAdapter adapter = new MyViewPager(this,R.layout.list_item);
            adapter.setViewPager(viewPager);//替换viewPager.setAdapter(adapter);
            
        }
    }
     
