package dayou.com.amap_test_marter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import dayou.com.amap_test_marter.utils.ImageUtils;

public class MainActivity extends Activity {

    private Context mContext;
    MapView mapView;
    AMap aMap;

    double x;
    double y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mapView = (MapView) findViewById(R.id.map_id);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(mContext, aMap.getMapScreenMarkers().size() + "", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        for (int i = 0; i < PointInfo.latlngArr.length; i++) {

            String[] xyArr = PointInfo.latlngArr[i].split(",");
            x = Double.parseDouble(xyArr[0]);
            y = Double.parseDouble(xyArr[1]);

            LatLng latLng1 = new LatLng(x, y);

            addMarker(PointInfo.imgArr[i], latLng1);
        }


    }

    private synchronized void addMarker(String url, final LatLng latLng) {

        View markerView = LayoutInflater.from(mContext).inflate(R.layout.markxml, null);
        ImageView markView = (ImageView) markerView.findViewById(R.id.icon_iv);

        Glide.with(this)
                .load(url).
                asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(markView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        if (resource != null) {
                            RoundedBitmapDrawable drawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            drawable.setCircular(true);
                            if (drawable != null) {
                                Bitmap makerMap = ImageUtils.toRoundBitmap(drawable.getBitmap());
                                if (makerMap != null) {

                                    MarkerOptions options = new MarkerOptions().anchor(0.5f, 0.5f)
                                            .position(latLng).title("lall").snippet("aaaa" + 1).
                                                    icon(BitmapDescriptorFactory.fromBitmap(makerMap))
                                            .draggable(true);
                                    aMap.addMarker(options);

                                }
                            }
                        }
                    }
                });
    }
}
