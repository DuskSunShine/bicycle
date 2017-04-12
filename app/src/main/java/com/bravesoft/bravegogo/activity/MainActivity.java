package com.bravesoft.bravegogo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bravesoft.bravegogo.R;
import com.bravesoft.bravegogo.bean.bikesBean;
import com.bravesoft.bravegogo.interfaces.Activitys;
import com.bravesoft.bravegogo.utils.ChangeDrawImgInch;
import com.bravesoft.bravegogo.utils.HandlerNetUtil;
import com.bravesoft.bravegogo.utils.NetWorkUtils;
import com.bravesoft.bravegogo.utils.PoiOverlay;
import com.bravesoft.bravegogo.utils.WalkingRouteOverlay;
import com.bravesoft.bravegogo.zxing.activity.CaptureActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,BDLocationListener {
    private DrawerLayout drawLayout;//侧边抽屉
    private SharedPreferences sp;
    private static final String ISFIRST = "first";
    private ImageView ic_activity, common_more, mapRefresh, onceLocation,map_pin;
    private CircleImageView headImg, common_avatar;
    private TextView phoneNum,direction,duration,addr,bicycleTime,pay;
    private Button finish, btnOrder, btnWallet, btn_mapScan,
            btnCredit, btnInvite, btnFeedback, btnSetting,go,exp;
    private PopupWindow popwindow,p,b;
    private LinearLayout popL1, popL2;
    private RelativeLayout hr;
    private long exitTime = 0;//退出，初始时间
    private boolean isLogin = false;//是否已登录
    private MapView mapView;
    private LocationClient locationClient;
    private Animation animation;
    private  double lat, lng;
    private boolean isLocationed = false;//定位成功
    private int isFirstSearch=1;//是否第一次检索
    private myPoiOverlay poiOverlay;
    private float mCurrentX;
    private MyOrientationListener myOrientationListener;
    private Marker marker;//定位标记
    private PoiSearch poiSearch;
    private boolean isRouteDrag=true;//路线规划时拖动地图
    private myRouteOverlay overlay;
    private int time=0;//骑行时间默认0分钟
    private int money=0;//支付费用
    private Handler handler;
    private Timer timer;
    private RoutePlanSearch walkSearch;//步行路线搜索
    private List<bikesBean.BikesBean> bikes;
    private bikesBean.BikesBean bike;
    private boolean isMove = false;//用户手指是否滑动
    private int down_x;//手指落下时的x坐标
    private int down_y;//手指落下时的y坐标
    private boolean isMaked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activitys.activitys.add(this);
        initView();
        ChangeDrawImgInch.changeInch(this, R.mipmap.map_scan, btn_mapScan);
        initLocation();
        NoNetwork();
        mapDrag();
        myOrientationListener=new MyOrientationListener(this);
        myOrientationListener.start();
        poiOverlay=new myPoiOverlay(mapView.getMap());

        //markClick();
        // TODO:   更新时间和费用
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg!=null){
                    bicycleTime.setText(""+msg.arg1);
                    pay.setText(""+msg.arg2);
                }
            }
        };
    }

    //网络状态，有网在定位
    private void NoNetwork(){
        if (NetWorkUtils.isConnect(this)){
            refresh();
            locationClient.start();

        }else {
            exp.setVisibility(View.VISIBLE);
            mapRefresh.setVisibility(View.INVISIBLE);
        }
    }
    //mapRefresh的刷新
    private void refresh(){
        mapRefresh.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        animation.setInterpolator(new LinearInterpolator());//很重要，不然旋转在360度会停顿
        mapRefresh.startAnimation(animation);
    }
    //mapRefresh隐藏,结束刷新
    private void unRefresh(){
        mapRefresh.setVisibility(View.INVISIBLE);
        mapRefresh.clearAnimation();
    }
    //初始化，设置定位参数
    private void initLocation() {
        //隐藏地图比例尺，缩放控件
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        //隐藏罗盘
        mapView.getMap().getUiSettings().setCompassEnabled(false);
        mapView.getMap().setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启定位图层
        mapView.getMap().setMyLocationEnabled(true);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //存储sp值
        sp = getSharedPreferences(ISFIRST, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("msg", false);
        editor.commit();
        //修改昵称
        sp = getSharedPreferences("nick", MODE_PRIVATE);
        String mNick = sp.getString("mNick", null);
        if (mNick != null) {
            phoneNum.setText(mNick);
        }
        //头像设置
        sp = getSharedPreferences("ImgHeadPath", MODE_PRIVATE);
        String path = sp.getString("path", null);
        if (path != null) {
            Glide.with(MainActivity.this)
                    .load("file://" + path)
                    .centerCrop().into(headImg);
            Glide.with(MainActivity.this)
                    .load("file://" + path)
                    .centerCrop().into(common_avatar);
        }

        Intent intent = getIntent();
        if (intent != null) {
            String num = intent.getStringExtra("num");
            phoneNum.setText(num);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.stop();
        locationClient.unRegisterLocationListener(this);
        myOrientationListener.stop();
        mapView.getMap().setMyLocationEnabled(false);
    }

    //加载控件,设置监听
    private void initView() {
        drawLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        ic_activity = (ImageView) findViewById(R.id.ic_activity);
        common_more = (ImageView) findViewById(R.id.common_more);
        headImg = (CircleImageView) findViewById(R.id.headImg);
        common_avatar = (CircleImageView) findViewById(R.id.common_avatar);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        finish = (Button) findViewById(R.id.finish);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnWallet = (Button) findViewById(R.id.btnWallet);
        btnCredit = (Button) findViewById(R.id.btnCredit);
        btnInvite = (Button) findViewById(R.id.btnInvite);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btn_mapScan = (Button) findViewById(R.id.btn_mapScan);
        mapRefresh = (ImageView) findViewById(R.id.mapRefresh);
        onceLocation = (ImageView) findViewById(R.id.onceLocation);
        map_pin= (ImageView) findViewById(R.id.map_pin);
        exp= (Button) findViewById(R.id.exp);
        hr= (RelativeLayout) findViewById(R.id.hr);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_order, btnOrder);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_wallet, btnWallet);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_credit, btnCredit);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_invite, btnInvite);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_feedback, btnFeedback);
        ChangeDrawImgInch.changeInch(this, R.mipmap.user_center_setting, btnSetting);
        headImg.setOnClickListener(this);
        finish.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnWallet.setOnClickListener(this);
        btnCredit.setOnClickListener(this);
        btnInvite.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btn_mapScan.setOnClickListener(this);
        common_more.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.mapView);
        onceLocation.setOnClickListener(this);
        exp.setOnClickListener(this);
    }

    //点击头像打开侧滑布局
    public void startDraw(View view) {
        sp = getSharedPreferences("isLogin", MODE_PRIVATE);
        isLogin = sp.getBoolean("login", false);
        if (!isLogin) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            drawLayout.openDrawer(Gravity.LEFT, true);
        }
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headImg:
                startActivity(new Intent(this, PersonalInfo.class));
                break;
            case R.id.finish:
                startActivity(new Intent(this, PaymentActivity.class));
                break;
            case R.id.btnOrder:
                startActivity(new Intent(this, TravelRecordActivity.class));
                break;
            case R.id.btnFeedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.btnWallet:
                startActivity(new Intent(this, WalletActivity.class));
                break;
            case R.id.btnCredit:
                startActivity(new Intent(this, CreditActivity.class));
                break;
            case R.id.btnInvite:
                startActivity(new Intent(this, FriendInviActivity.class));
                break;
            case R.id.btnSetting:
                startActivity(new Intent(this, InstallActivity.class));
                break;
            case R.id.btn_mapScan:
                if (btn_mapScan.getText().equals("扫码解锁")){
                    Intent intent = new Intent(this, CaptureActivity.class);
                    intent.putExtra("mainScan", 1);
                    startActivity(intent);
                }else {
                    btn_mapScan.setText("扫码解锁");
                    b.dismiss();
                    timer.cancel();
                    Toast.makeText(MainActivity.this,"支付"+money+"元成功，用车结束",Toast.LENGTH_SHORT).show();
                    locationClient.start();
                    //poiFind(lat,lng);
                }

                break;
            case R.id.common_more:
                View popView = LayoutInflater.from(this).inflate(R.layout.popupwindowdot, null);
                popL1 = (LinearLayout) popView.findViewById(R.id.popL1);
                popL2 = (LinearLayout) popView.findViewById(R.id.popL2);
                popwindow = new PopupWindow(popView, 220, 180);
                popwindow.setFocusable(true);
                popwindow.setOutsideTouchable(true);
                popwindow.setOutsideTouchable(true);
                popwindow.setBackgroundDrawable(new BitmapDrawable());
                popwindow.showAsDropDown(common_more);
                popL1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popwindow.dismiss();
                    }
                });
                popL2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popwindow.dismiss();
                    }
                });
                break;
            //点击再次定位
            case R.id.onceLocation:
                //onceLocation.setImageResource(R.mipmap.map_position_3d);
               /* isFirstSearch=2;
                if (poiOverlay!=null){
                poiOverlay.removeFromMap();
                }*/
                refresh();
                locationClient.start();
                break;
            case R.id.exp:
                if (NetWorkUtils.isConnect(this)){
                    exp.setVisibility(View.GONE);
                    refresh();
                    locationClient.start();
                }else {
                    exp.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //连点两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(MainActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                for (AppCompatActivity str : Activitys.activitys) {
                    str.finish();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    //定位回调方法
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation!=null){
            if (bdLocation.getLocType() == 61 || bdLocation.getLocType() == 161) {
                lat = bdLocation.getLatitude();
                lng = bdLocation.getLongitude();
                Log.e("==lat,lng==",lat+","+lng);
                //mapCenter(lat, lng);//中心
                direction(bdLocation);
                //nearbyBikes();
                if (isFirstSearch==1){
                    poiFind(lat,lng);
                    isFirstSearch=2;
                }else {
                    poiOverlay.removeFromMap();
                    poiFind(lat,lng);
                }
                //direction(bdLocation);//方向
                //onceLocation.setImageResource(R.mipmap.map_position_grey);
                unRefresh();
                locationClient.stop();
            }
        }
    }
    //周边自行车信息,定位成功就显示数据进行mark
    private void nearbyBikes(){
        String path="http://cd.dev.vc/android/bike.json";
        HandlerNetUtil.doNetwork(path, new HandlerNetUtil.DataCallback() {
            @Override
            public void doData(byte[] bytes) {
                String str=new String(bytes);
                Log.e("==str==",str);
                try {
                    JSONObject object=new JSONObject(str);
                    JSONArray array=object.optJSONArray("bikes");
                    Log.e("==array==",array+"");
                    bikes=new ArrayList<bikesBean.BikesBean>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.optJSONObject(i);
                        Log.e("==jsonObject==",jsonObject+"");
                        String id = jsonObject.optString("id");
                        String status = jsonObject.optString("status");
                        String location = jsonObject.optString("location");
                        bike=new bikesBean.BikesBean();
                        bike.setId(id);
                        bike.setStatus(status);
                        bike.setLocation(location);
                        bikes.add(bike);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        //标记
        //mapMarker(104.073462,30.635483,R.mipmap.map_adress);
        for (int i = 0; i <bikes.size() ; i++) {
            String location = bikes.get(i).getLocation();
            String[] split = location.split(",");
            mapMarker(Double.parseDouble(split[1])
                    ,Double.parseDouble(split[0]),R.mipmap.map_adress);
            Bundle bundle=new Bundle();
            bundle.putSerializable("status",bikes.get(i).getStatus());
            marker.setExtraInfo(bundle);
            Log.e("==location==",Double.parseDouble(split[1])+","
                    +Double.parseDouble(split[0])+bikes.size());
        }
    }
    //标记点点击
    private void markClick(){
        //boolean isFirstOne=true;//是否是第一个被点击的
        mapView.getMap().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
               /* for (int i = 0; i < bikes.size(); i++) {
                    status = bikes.get(i).getStatus();
                    Log.e("==status==",bikes.get(i).getStatus());
                }*/
                Serializable status = marker.getExtraInfo().getSerializable("status");
                if (status.equals("free")){
                    //步行路线规划
                    walkSearch=RoutePlanSearch.newInstance();
                    OnGetRoutePlanResultListener listener=new OnGetRoutePlanResultListener() {
                        @Override
                        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                            //步行线路规划结果
                            if (walkingRouteResult!=null){
                                //mapCenter(lat,lng);
                                isRouteDrag=true;
                                List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
                                overlay=new myRouteOverlay(mapView.getMap());
                                //if (isFirstOne) {
                                overlay.setData(routeLines.get(0));
                                overlay.addToMap();
                                // isFirstOne=false;
                                //}
                                //overlay.zoomToSpan();
                                //弹窗
                                popGo();

                                addr.setText("成都市武侯区人民南路三段");
                                direction.setText(routeLines.get(0).getDistance()+"");
                                duration.setText(routeLines.get(0).getDuration()/60+"");
                                Log.e("=距离=",routeLines.get(0).getDistance()+"=="+routeLines.get(0).getDuration());

                            }
                        }

                        @Override
                        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

                        }

                        @Override
                        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

                        }

                        @Override
                        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

                        }

                        @Override
                        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

                        }

                        @Override
                        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

                        }
                    };
                    walkSearch.setOnGetRoutePlanResultListener(listener);
                    //检索起点终点
                    PlanNode stNode=PlanNode.withLocation(new LatLng(lat,lng));
                    PlanNode enNode=PlanNode.withLocation(marker.getPosition());
                    walkSearch.walkingSearch(new WalkingRoutePlanOption()
                            .from(stNode).to(enNode));
                }else if (status.equals("reserved")){
                    Toast.makeText(MainActivity.this,"该车使用中",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
    private void poiFind(double lat,double lng){
        poiSearch=PoiSearch.newInstance();
        OnGetPoiSearchResultListener resultListener=new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
               poiOverlay.setData(poiResult);
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
                mapView.getMap().setOnMarkerClickListener(poiOverlay);
                isRouteDrag=false;
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(resultListener);
        poiSearch.searchNearby(new PoiNearbySearchOption()
        .keyword("小吃").location(new LatLng(lat,lng)).radius(10000));
    }
    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
    //将中心点移动到定位位置,放大18倍
    private void mapCenter(double lat, double lng) {
        MapStatus mapStatus = new MapStatus.Builder()
                .target(new LatLng(lat, lng)).zoom(18).build();//
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        //改变地图状态
        mapView.getMap().setMapStatus(mMapStatusUpdate);
    }

    //地图定位点标记
    private void mapMarker(double lat, double lng, int Id) {
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(Id);
        OverlayOptions options = new MarkerOptions()
                .position(new LatLng(lat, lng)).icon(descriptor).zIndex(9);
        marker= (Marker) mapView.getMap().addOverlay(options);
    }
    //方向改变
    private void direction(BDLocation location){
        MyLocationData data = new MyLocationData.Builder()
                .direction(mCurrentX)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        //图标及配置
        MyLocationConfiguration configuration=new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,true,
                BitmapDescriptorFactory.fromResource(R.mipmap.map_direction));
        mapView.getMap().setMyLocationConfigeration(configuration);
        mapView.getMap().setMyLocationData(data);
        myOrientationListener.setmOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX=x;
            }
        });
    }

    //地图拖动，根据手机中心点从新定位
    private void mapDrag(){
        mapView.getMap().setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //根据像素点反获取地图坐标，实现POI检索
                int[] location=new int[2];
                map_pin.getLocationOnScreen(location);
                int x=location[0];
                int y=location[1];
                LatLng geoPoint=mapView.getMap().getProjection().fromScreenLocation(new Point(x,y));
                if (isFirstSearch==2) {
                    poiOverlay.removeFromMap();
                }
                if (!isRouteDrag){
                    poiFind(geoPoint.latitude, geoPoint.longitude);
                }

                Log.e("==geoPoint==",geoPoint.latitude+","+geoPoint.longitude);
            }
        });
    }
    //预约弹窗
    private void popGo() {
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.popgo, null);
        direction= (TextView) inflate.findViewById(R.id.direction);
        duration= (TextView) inflate.findViewById(R.id.duration);
        addr= (TextView) inflate.findViewById(R.id.addr);
        go= (Button) inflate.findViewById(R.id.go);
        p = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       /* p.setFocusable(true);
        p.setOutsideTouchable(true);
        p.setBackgroundDrawable(new BitmapDrawable());*/
        p.showAtLocation(mapView,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        // TODO:  弹窗的消失时机
        p.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                overlay.removeFromMap();
                locationClient.start();
                isRouteDrag=false;
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.dismiss();
                overlay.removeFromMap();
                //用车中弹窗
                View bicycle=LayoutInflater.from(MainActivity.this).inflate(R.layout.bicycleing,null);
                bicycleTime= (TextView) bicycle.findViewById(R.id.bicycleTime);
                pay= (TextView) bicycle.findViewById(R.id.pay);
                b=new PopupWindow(bicycle,
                        ViewGroup.LayoutParams.MATCH_PARENT, 300);
                b.setBackgroundDrawable(new BitmapDrawable());
                b.showAsDropDown(hr);
                btn_mapScan.setText("结束用车");
                //// TODO: 2017/3/24 计时
                timer=new Timer(true);
                TimerTask timertask=new TimerTask() {
                    @Override
                    public void run() {
                        time++;
                        money++;
                        Message message=new Message();
                        message.arg1=time;
                        message.arg2=money;
                        handler.sendMessage(message);
                    }
                };
                timer.schedule(timertask,60000,60000);
                locationClient.start();
            }
        });
    }
    //点击标记点,路线规划
    class myPoiOverlay extends PoiOverlay{
        RoutePlanSearch walkSearch;//步行路线搜索
        private boolean isFirstOne=true;//是否是第一个被点击的
        /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */
        public myPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            final PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            Log.e("==onPoiClick==",poiInfo.name+"==="+poiInfo.address
                    +"==="+poiInfo.describeContents()+"==="+poiInfo.isPano);
            //步行路线规划
            walkSearch=RoutePlanSearch.newInstance();
            OnGetRoutePlanResultListener listener=new OnGetRoutePlanResultListener() {
                @Override
                public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                    //步行线路规划结果
                    if (walkingRouteResult!=null){
                        //mapCenter(lat,lng);
                        isRouteDrag=true;
                        List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
                         overlay=new myRouteOverlay(mapView.getMap());
                        if (isFirstOne) {
                                overlay.setData(routeLines.get(0));
                                overlay.addToMap();
                                isFirstOne=false;
                            }
                            //overlay.zoomToSpan();
                            //弹窗
                            popGo();

                            addr.setText(poiInfo.address);
                            direction.setText(routeLines.get(0).getDistance()+"");
                            duration.setText(routeLines.get(0).getDuration()/60+"");
                            Log.e("=距离=",routeLines.get(0).getDistance()+"=="+routeLines.get(0).getDuration());

                    }
                }

                @Override
                public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

                }

                @Override
                public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

                }

                @Override
                public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

                }

                @Override
                public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

                }

                @Override
                public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

                }
            };
            walkSearch.setOnGetRoutePlanResultListener(listener);
            //检索起点终点
            PlanNode stNode=PlanNode.withLocation(new LatLng(lat,lng));
            PlanNode enNode=PlanNode.withLocation(poiInfo.location);
            walkSearch.walkingSearch(new WalkingRoutePlanOption()
                    .from(stNode).to(enNode));
            return true;
        }
    }
}

//路线规划配置
class myRouteOverlay extends WalkingRouteOverlay{

    public myRouteOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.navi_orgin);
    }

    @Override
    public BitmapDescriptor getTerminalMarker() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.navi_arrived_destination);
    }
}
//方向传感器
class MyOrientationListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context context;
    private Sensor mSensor;// 传感器

    private float lastX;

    public MyOrientationListener(Context context) {
        this.context = context;
    }

    // 开启监听
    @SuppressWarnings("deprecation")
    public void start() {
        //获得系统服务
        mSensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager!=null){
            //获得方向传感器
            mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(mSensor!=null){
            mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_UI);//第三个参数为经度
        }
    }

    // 结束监听
    public void stop() {
        //停止定位
        mSensorManager.unregisterListener(this);
    }

    // 经度发生改变时
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    // 纬度发生改变时
    @Override
    public void onSensorChanged(SensorEvent event) {
        //如果事件返回的类型是方向传感器
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
            float x=event.values[SensorManager.DATA_X];
            //如果变化大于一度
            if(Math.abs(x-lastX)>1.0){
                //通知主界面进行回掉
                if(mOnOrientationListener!=null){
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }
            lastX=x;
        }
    }


    private OnOrientationListener mOnOrientationListener;

    public void setmOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    //回掉接口
    public interface OnOrientationListener{
        void onOrientationChanged(float x);
    }
}