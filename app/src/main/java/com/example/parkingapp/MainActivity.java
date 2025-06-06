package com.example.parkingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAinActivity";
    private static final String STATES = "STATES";
    private static final float ZOOM_FACTOR = 1.15f;
    private static  float currentScale = 1f;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(STATES, "Создается Main Activity");
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ImageView mapView = findViewById(R.id.MapVeiw);
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        SVGTransformer transformer = new SVGTransformer(this, "map1.svg");
        transformer.renderToImageView(mapView);

        mapView.setOnTouchListener(createListenerForMoveMap());
        setZooming(mapView);

        ImageButton selectMap = findViewById(R.id.SelectMapButton);

        Intent intent = new Intent(this, SelectMap.class);
        selectMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }


    private void setZooming(ImageView mapView) {
        ImageButton zoomInButton =  findViewById(R.id.ZoomInButton);
        zoomInButton.setOnClickListener(v -> zoomIn(mapView));

        ImageButton zoomOutButton = findViewById(R.id.ZoomOutButton);
        zoomOutButton.setOnClickListener(v -> zoomOut(mapView));
    }


    private void zoomIn(ImageView v){
        currentScale *= ZOOM_FACTOR;
        v.setScaleX(currentScale);
        v.setScaleY(currentScale);
    }

    private void zoomOut(ImageView v){
        currentScale /= ZOOM_FACTOR;
        v.setScaleX(currentScale);
        v.setScaleY(currentScale);
    }

    private View.OnTouchListener createListenerForMoveMap(){
        return new View.OnTouchListener(){
            float dX, dY;
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;

                    default:
                        return false;
                }
                return true;}
                 };}


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(STATES, "Activity on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(STATES, "Activity on Stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent_from_select_map = getIntent();
        Log.d(STATES,
        "On start from intent: " + String.format("%s", intent_from_select_map.getStringExtra("name")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(STATES, "Activity on Resume");
    }
}