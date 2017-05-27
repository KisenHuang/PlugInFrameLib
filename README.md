# PlugInFrameLib
一个基于MVP开发框架代码库，PlugInFrameLib集成了OkHttp3，RecyclerView，Glide，EventBus，ButterKnife
和一些工具方法。PlugInFrameLib模块化开发，代码开发效率高，使用方便，在上层已经通过mvp思想进行结偶，减少代码量。

# 优势
-------
## 插件化设计，使用方便，灵活性高
1、本框架设计来自于对 Activity 和 Fragment 生命周期的 发觉，主要通过ActivityLifecycleCallbacks
和FragmentManager.FragmentLifecycleCallbacks分别注册到Application和FragmentManager中。使用时只需要实现
 BaseView 和 BaseListView 接口就可以了，不用担心其他框架也会需要继承BaseActivity的情况了。
2、代码中使用类范型，调用者不用过多思考都需要创建什么类，根据范型和接口方法的提示操作就可以了，完成功能了。
## 未使用反射
大大降低了对系统性能的消耗。
## 减少内存泄漏的产生
在封装过程中，充分利用了，Activity.onDestroy 和 Fragment.onDestroy 方法，对mvp架构中涉及到的类进行回收。

# MVP实现
-------
## Presenter
负责View与Model之间的交互和数据通信，这是Presenter定义与实现

    public interface IPresenter {

         /**
          * Presenter被初始化时调用
          *
          * @param view Activity对象
          */
         void attachView(View view);

         /**
          * Activity被销毁时调用
          * {@link Activity#onDestroy()}
          */
         void detachView();
    }

    /**
     * 普通页面使用的Presenter
     */
    public class BasePresenter implements IPresenter {

        private View view;

        protected View getView() {
            return view;
        }

        @Override
        public void attachView(View view) {
            this.view = view;
        }

        @Override
        public void detachView() {
            view = null;
        }
    }

    /**
     * 列表Presenter
     */
     public abstract class BaseListPresenter implements IPresenter {

        ...

     }


## View
主要负责UI交互，通过init、newPresenter初始化，注newPresenter只能被调用一次，不允许使用者调用，
需要保证View中presenter对象不变。openLoadingAnim和closeLoadingAnim是进行有延时操作时对Loading效果的控制。
handleSuccess，handleFail，handleFinish 是Model处理数据的结果回调方法。

    interface MvpView<P extends BasePresenter> {

        /**
         * 返回上下文
         */
        Context getContext();

        /**
         * 返回布局
         */
        int getLayoutId();

        /**
         * 初始化操作
         */
        void init(P presenter);

        /**
         * 创建Presenter
         * 该方法不允许被调用
         *
         * @return IPresenter实现类
         */
        P newPresenter();

        /**
         * 网络请求成功结果回调
         *
         * @param result 返回结果
         * @param id     请求识别码，本地识别
         */
        void handleSuccess(String result, int id);

        /**
         * 网络请求失败结果回调
         *
         * @param e 失败异常结果
         * @param id     请求识别码，本地识别
         */
        void handleFail(Exception e, int id);

        /**
         * 网络请求结束回调
         * 在success()方法和fail()方法之后调用
         * @param id     请求识别码，本地识别
         */
        void handleFinish(int id);
    }

    /**
     * 普通界面实现
     */
    public interface BaseView<P extends BasePresenter> extends MvpView<P> {

        /**
         * 打开加载动画
         */
        void openLoadingAnim();

        /**
         * 关闭加载动画
         */
        void closeLoadingAnim();
    }

    /**
     * 列表界面实现
     */
    public interface BaseListView<P extends BaseListPresenter> extends BaseView<P> {

        ...

        /**
         * 返回列表的长度
         * 用于判断是否开启加载更多功能
         */
        int getPageSize();

        /**
         * 加载数据
         */
        void loadData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoad();
    }

## Model
数据处理，封装了OkHttp3。将获取数据通过Presenter传递给View

   public abstract class MvpModel {

       protected BasePresenter presenter;

       protected Context context;

       private static final String TAG = "MvpModel";

       public MvpModel(BasePresenter presenter) {
           this.presenter = presenter;
           context = presenter.getView().getContext();
       }

       /**
        * post String 请求
        *
        * @param url      请求url
        * @param content  body
        * @param reqCode  请求码
        * @param callback 请求回调
        */
       protected void postString(String url, String content, @HttpMediaType.ContentType String contentType,
                                 int reqCode, final NetWorkCallback<String> callback) {
           HttpClient.postString(context, url, content, contentType, reqCode, getCallback(callback));
       }

       /**
        * post请求
        *
        * @param url      请求url
        * @param param    请求参数
        * @param reqCode  请求码
        * @param callback 请求回调
        */
       protected void post(String url, RequestParam param, int reqCode, final NetWorkCallback<String> callback) {
           HttpClient.post(context, url, param, reqCode, getCallback(callback));
       }

       /**
        * get请求
        *
        * @param url      请求url
        * @param param    请求参数
        * @param reqCode  请求码
        * @param callback 请求回调
        */
       protected void get(String url, RequestParam param, int reqCode, final NetWorkCallback<String> callback) {
           HttpClient.get(context, url, param, reqCode, getCallback(callback));
       }

       /**
        * put请求
        *
        * @param url      请求url
        * @param content  body
        * @param reqCode  请求码
        * @param callback 请求回调
        */
       protected void put(String url, String content, @HttpMediaType.ContentType String contentType,
                          int reqCode, final NetWorkCallback<String> callback) {
           HttpClient.put(context, url, content, contentType, reqCode, getCallback(callback));
       }

       /**
        * delete请求
        *
        * @param url      请求url
        * @param content  body
        * @param reqCode  请求码
        * @param callback 请求回调
        */
       protected void delete(String url, String content, @HttpMediaType.ContentType String contentType,
                             int reqCode, final NetWorkCallback<String> callback) {
           HttpClient.delete(context, url, content, contentType, reqCode, getCallback(callback));
       }

       /**
        * 封装callback
        * 所有请求的回调必须经过这一层封装
        * 它会通知BaseView的请求回调
        *
        * @param callback 返回的数据
        * @return 封装后的callback
        */
       @NonNull
       protected NetWorkCallback<String> getCallback(final NetWorkCallback<String> callback) {
           return new NetWorkCallback<String>() {
               @Override
               @SuppressWarnings("unchecked")
               public void success(String result, int id) {
                   presenter.getView().handleSuccess(result, id);
                   callback.success(result, id);
               }

               @Override
               public void fail(Exception e, int id) {
                   presenter.getView().handleFail(e, id);
                   callback.fail(e, id);
               }

               @Override
               public void finish(int id) {
                   presenter.getView().handleFinish(id);
                   callback.finish(id);
               }
           };
       }
   }

## Data
数据模型，默认实现Parcelable接口。
定义Data的意义在于，再打包时，数据模型类不能被混淆，可以
-keep class * implements {包名.Data路径}.Data{*;}

# 增加对列表的mvp实现
-------
其实列表视图(RecyclerView、ListView)、Adapter、Model、Data。同样是mvp的体现，也可以使用mvp框架去实现。
这里以RecyclerView为例(RecyclerView灵活，依赖少，可拓展性强，效果也很棒，建议大家使用)

## Adapter
一般我们在写列表界面时都会写很多Adapter，代码重复，无非就是onBindViewHolder(),getItemViewType(),getItemCount()
和onCreateViewHolder()方法，我们可以将Adapter提取，把代码转移到其他地方，定义IAdapter接口。

    public interface IAdapter {

        /**
         * 需要实现，返回对应Item的布局文件Id 如果返回0，则使用适配器默认布局
         *
         * @return 返回当前数据类对应布局
         */
        int getItemResId();

        /**
         * 必须实现，在数据类中直接将数据适配到通过BaseViewHolder获取到的视图中
         *
         * @param helper          用来获取Item的控件
         * @param adapterPosition 该Item在Adapter中的位置
         *                        {@link android.widget.BaseAdapter#getView(int, View, ViewGroup)}
         */
        void onBindViewHolder(BaseViewHolder helper, int adapterPosition);

        /**
         * 需要实现，默认返回0，同一列表中出现多种不同的布局时，必须返回不同的类型，
         * 如果返回相同的值，会因BaseViewHolder复用出现布局错乱，处理数据时异常
         * 在{@link IAdapter#getItemResId()}中已经把对应的布局返回给适配器
         *
         * @return 返回当前自定义Item类型
         * {@link android.widget.BaseAdapter#getItemViewType(int)}
         */
        int getItemType();

        /**
         * 在Adapter中获取到的Item的位置数据
         *
         * @return item在adapter中的位置
         */
        int getItemPosition();
    }

自定义Adapter，利用IAdapter将逻辑转移：

    public class BaseAdapter<I extends IAdapter> extends RecyclerView.Adapter<BaseViewHolder> {

        private List<I> mItems;
        private int itemPos;

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(mItems.get(itemPos).getItemResId(), parent, false);
            return new BaseViewHolder(v);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            itemPos = holder.getAdapterPosition();
            mItems.get(position).onBindViewHolder(holder, position);
        }

        @Override
        public int getItemViewType(int position) {
            return mItems.get(position).getItemType();
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        ...
    }

这样我们就可以避免XXXAdapter，只需要一个BaseAdapter就够了，如果有特殊要求也可以重新实现。
剩下来就需要实现IAdapter了。

## Item实现
但是这样会带来一个问题：数据一般是由网络请求得到的，不会添加过多的类引用(例如：Context上下文,逻辑处理对象等)
这样就会导致局限性，当我们有更多需求时，我们就需要专门定义一个Item类实现IAdapter，并且增加相应的API：

    public abstract class Item<D extends Data> implements IAdapter, Interact<D>, View.OnClickListener {

        protected D data;
        protected ItemLogic logic;
        protected BaseAdapter adapter;
        protected int position;
        protected Context mContext;

        @Override
        public void onBindViewHolder(BaseViewHolder helper, int adapterPosition) {
            position = adapterPosition;
            setViewData(helper);
            helper.itemView.setEnabled(itemEnable());
            helper.itemView.setOnClickListener(this);
            onRefreshViewStyle();
        }

        @Override
        public int getItemType() {
            //默认返回 0，可重写
            return 0;
        }

        @Override
        public int getItemPosition() {
            return position;
        }

        @Override
        public void onClick(View v) {
            if (logic != null && logic.isReady()) {
                logic.onItemClick(adapter, this);
            }
            onItemClick(v);
        }

        /**
         * 返回Item持有数据
         */
        public D getData() {
            return data;
        }

        /**
         * 设置处理逻辑
         */
        public void setLogic(ItemLogic logic) {
            this.logic = logic;
        }

        /**
         * 给Item设置数据
         */
        public void setData(D data) {
            this.data = data;
        }

        /**
         * 设置Adapter
         */
        public void setAdapter(BaseAdapter adapter) {
            this.adapter = adapter;
        }

        /**
         * 设置上下文
         */
        public void setContext(Context context) {
            mContext = context;
        }
    }

Item类还是实现了Interact接口，Interact接口主要是一些Item的交互与逻辑

    public interface Interact<D extends Data> {
        /**
         * 用于更新UI样式
         */
        void onRefreshViewStyle();

        /**
         * 得到Data数据，显示在Item上
         *
         * @param helper item UI持有对象
         * @see IAdapter setViewData(Context context,BaseViewHolder helper, int adapterPosition)
         */
        void setViewData(BaseViewHolder helper);

        /**
         * 设置Item是否可以点击
         */
        boolean itemEnable();

        /**
         * 条目点击事件。
         * {@link Interact#itemEnable()}必须返回true，这个方法才会被调用
         *
         * @param v item对应View
         */
        void onItemClick(View v);

        /**
         * 工厂方法创建Item时调用的方法
         *
         * @return 返回一个新的Item实例
         */
        Item<D> newSelf();

        /**
         * Item被创建时，设置完数据后调用，
         * 在{@link Item#onBindViewHolder(BaseViewHolder, int)}之前被调用，
         * 只被调用一次
         */
        void readyTodo();
    }

到目前为止列表的MVP框架已经建好了。View(Adapter)、Presenter(Item)。
相信大家也看到了上面代码中有个ItemLogic类，这是为了专门处理列表Item之间关系而设计的。
现在我有这样一个需求：在一个列表中，实现Item多选，并返回选择的结果。
那我们继续：

    /**
     * 列表Presenter
     * <p>
     * 绑定{@link ItemLogic}列表逻辑处理
     * 生成默认BaseAdapter
     * 使用ItemFactory生成Item列表
     * </p>
     * Created by huang on 2017/2/7.
     */
    public abstract class BaseListPresenter<D extends Data> extends BasePresenter {

        private Item<D> mItemTemplate;
        private ItemFactory<D> factory;
        protected ItemLogic itemLogic;
        private BaseAdapter<Item<D>> adapter;

        @Override
        public void attachView(View view) {
            super.attachView(view);
            mItemTemplate = setupItemTemplate();
            adapter = new BaseAdapter<>();
            factory = new ItemFactory<>(view, mItemTemplate, adapter);
        }

        /**
         * 在父类中注册ItemLogic，
         * 主要是在创建Item时传给所有Item，保持所有Item都持有一个ItemLogic对象
         * {@link ItemFactory#makeItems(List, ItemLogic)}
         *
         * @param logic 在父类中注册的ItemLogic
         */
        protected void setItemLogic(ItemLogic logic) {
            itemLogic = logic;
        }

        @Override
        public void detachView() {
            super.detachView();
            if (itemLogic != null) {
                itemLogic.clear();
                itemLogic = null;
            }
            mItemTemplate = null;
            factory = null;
            adapter.clear();
        }

        /**
         * 通过list生产出Item列表
         * {@link ItemFactory#makeItems(List, ItemLogic)}
         *
         * @param list 生产Item所需数据源
         */
        public void notifyAfterLoad(List<D> list) {
            List<Item<D>> items = factory.makeItems(list, itemLogic);
            adapter.addData(items);
        }

        public BaseAdapter<Item<D>> getAdapter() {
            return adapter;
        }

        /**
         * 设置Item模板用于生产列表
         * {@link Item#newSelf()}
         *
         * @return 一个Item模板
         */
        protected abstract Item<D> setupItemTemplate();
    }

    public class ItemFactory<D extends Data> {

        ...

        @Nullable
        public List<Item<D>> makeItems(List<D> list, Logic logic, BaseListPresenter<D> presenter) {
            List<Item<D>> items = new ArrayList<>();
            if (list == null)
                return items;
            for (D d : list) {
                Item<D> item = presenter.setupItemTemplate();
                if (item instanceof SetLogic) {
                    ((SetLogic) item).setLogic(logic);
                }
                item.setContext(context);
                item.setAdapter(adapter);
                item.setData(d);
                item.readyTodo();
                items.add(item);
            }
            return items;
        }
    }

现在又增加了一个ItemLogic类，在BaseListPresenter中有一个ItemFactory工厂类，每一个Item都会持有ItemLogic对象，
我们通过ItemLogic实现Item之间的逻辑交互。说到这里，我们这个开发框架介绍完了。
这里我们提取了View、Presenter、BaseAdapter、Item，这样我们在使用时，普通页面只需要创建四个类
：Activity、Model、Presenter、Data，而列表界面就比较多了，需要创建六各类：Activity、ListPresenter、
Item、Model、Logic(如果不需要，可以不创建，而且可以重复使用)、Data;

# License

Copyright 2017 huang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
 is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 or implied. See the License for the specific language governing permissions and limitations
 under the License.