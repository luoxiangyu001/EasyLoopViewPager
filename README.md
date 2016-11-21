# EasyLoopViewPager
A Easy Loop ViewPager and Adapter for Android

一个简单的无限循环ViewPager。

实现主要原理是在头尾各加一个item，即障眼法。

#使用
1.继承EasyViewPager

    public class MyViewPager extends EasyViewPager{

        public MyViewPager(Context context) {
          super(context);
        }
    }
    
2.继承EasyViewPagerAdapter

    public class MyAdapter<T> extends EasyViewPagerAdapter{

      public MyAdapter(EasyViewPager viewPager, int layoutId) {
          super(viewPager, layoutId);
      }
      
      public void convert(View view, T item, int position){
        //TODO 在这里实现adapter布局
      }
    }
    
3.设置Adapter

    public class MyActivity extends AppCompatActivity {

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.my_activity);
            MyViewPager viewPager = (MyViewPager)findViewById(R.id.viewpager);
            MyAdapter adapter = new MyViewPager(this,R.layout.list_item);
        }
    }
     
