package com.cloud.resources.flowtrack;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/24
 * @Description:自定义纵向流程控件
 * @Modifier:
 * @ModifyContent:
 */
public class VerticalFlowTrackOneView extends View {

    //流程提示圈
    private Paint expressCirclePaint = null;
    private Paint expressCircleSelDarkPaint = null;
    private Paint expressCircleSelWhitePaint = null;
    private Paint expressSmallCircleSelDarkPaint = null;
    private TextPaint timeTextPaint = null;
    private TextPaint titlePaint = null;
    private TextPaint desPaint = null;
    private float timeTextSize = 0;
    private int timeTextColor = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    //每个flowItem距离
    private int perFlowItemSpacing = 0;
    private int timeSelectTextColor = 0;
    //左边视图宽度
    private int leftViewWidth = 0;
    //左边文本与节点间间距
    private int leftTextNodeSpacing = 0;
    //右边文本与节点间间距
    private int rightTextNodeSpacing = 0;
    //节点大小
    private int nodeSize = 0;
    //选中节点颜色
    private int selectNodeColor = 0;
    //节点颜色
    private int nodeColor = 0;
    //节点上面偏移量
    private int nodeTopOffset = 0;
    //标题文本大小
    private int titleTextSize = 0;
    //标题文本是否加粗
    private boolean titleTextBlod = false;
    //标题文本颜色
    private int titleTextColor = 0;
    //屏幕宽度
    private int screenWidth = 0;
    //描述性文本大小
    private int desTextSize = 0;
    //描述性文本颜色
    private int desTextColor = 0;
    //标题描述间距
    private int titleDesSpacing = 0;
    //距离底部边距
    private int paddingBottom = 0;
    //底部偏移量
    private int buttomOffset = 0;
    private List<Integer> timeKeys = new ArrayList<Integer>();
    private List<Integer> nodeKeys = new ArrayList<Integer>();

    public VerticalFlowTrackOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int fontSize14 = PixelUtils.dip2px(context, 14);
        int paintSize = PixelUtils.dip2px(context, 1);
        int circlePaintSize = PixelUtils.dip2px(context, 1);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalFlowTrackOneView, 0, 0);
        timeTextSize = a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_timeTextSize, fontSize14);
        timeTextColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_timeTextColor, Color.parseColor("#999999"));
        paddingLeft = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_paddingLeft, 24);
        paddingRight = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_paddingRight, 16);
        paddingTop = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_paddingTop, 32);
        timeSelectTextColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_timeSelectTextColor, Color.parseColor("#333333"));
        nodeSize = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_nodeSize, PixelUtils.dip2px(context, 12));
        selectNodeColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_selectNodeColor, Color.parseColor("#4C5B71"));
        nodeColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_nodeColor, Color.parseColor("#E7EFFE"));
        titleTextSize = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_titleTextSize, PixelUtils.dip2px(context, 16));
        titleTextBlod = a.getBoolean(R.styleable.VerticalFlowTrackOneView_vftov_titleTextBlod, false);
        titleTextColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_titleTextColor, Color.parseColor("#333333"));
        leftTextNodeSpacing = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_leftTextNodeSpacing, PixelUtils.dip2px(context, 16));
        rightTextNodeSpacing = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_rightTextNodeSpacing, PixelUtils.dip2px(context, 10));
        desTextSize = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_desTextSize, PixelUtils.dip2px(context, 13));
        desTextColor = a.getColor(R.styleable.VerticalFlowTrackOneView_vftov_desTextColor, Color.parseColor("#999999"));
        paddingBottom = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_paddingBottom, PixelUtils.dip2px(context, 30));
        leftViewWidth = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_leftViewWidth, PixelUtils.dip2px(context, 48));
        buttomOffset = (int) a.getDimension(R.styleable.VerticalFlowTrackOneView_vftov_buttomOffset, 0);
        a.recycle();
        expressCirclePaint = new Paint();
        expressCirclePaint.setColor(nodeColor);
        expressCirclePaint.setStyle(Paint.Style.FILL);
        expressCirclePaint.setAntiAlias(true);
        expressCirclePaint.setStrokeWidth(circlePaintSize);
        expressCircleSelDarkPaint = new Paint();
        expressCircleSelDarkPaint.setColor(selectNodeColor);
        expressCircleSelDarkPaint.setStyle(Paint.Style.FILL);
        expressCircleSelDarkPaint.setAntiAlias(true);
        expressCircleSelDarkPaint.setStrokeWidth(circlePaintSize);
        expressCircleSelWhitePaint = new Paint();
        expressCircleSelWhitePaint.setColor(Color.parseColor("#ffffff"));
        expressCircleSelWhitePaint.setStyle(Paint.Style.FILL);
        expressCircleSelWhitePaint.setAntiAlias(true);
        expressCircleSelWhitePaint.setStrokeWidth(circlePaintSize);
        expressSmallCircleSelDarkPaint = new Paint();
        expressSmallCircleSelDarkPaint.setColor(Color.parseColor("#4C5B71"));
        expressSmallCircleSelDarkPaint.setStyle(Paint.Style.FILL);
        expressSmallCircleSelDarkPaint.setAntiAlias(true);
        expressSmallCircleSelDarkPaint.setStrokeWidth(circlePaintSize);

        timeTextPaint = new TextPaint();
        timeTextPaint.setColor(timeTextColor);
        timeTextPaint.setStyle(Paint.Style.FILL);
        timeTextPaint.setAntiAlias(true);
        timeTextPaint.setTextSize(timeTextSize);
        timeTextPaint.setStrokeWidth(paintSize);

        titlePaint = new TextPaint();
        titlePaint.setColor(titleTextColor);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(titleTextSize);
        titlePaint.setStrokeWidth(paintSize);
        titlePaint.setFakeBoldText(titleTextBlod);

        desPaint = new TextPaint();
        desPaint.setColor(desTextColor);
        desPaint.setStyle(Paint.Style.FILL);
        desPaint.setAntiAlias(true);
        desPaint.setTextSize(desTextSize);
        desPaint.setStrokeWidth(paintSize);

        perFlowItemSpacing = PixelUtils.dip2px(context, 32);
        screenWidth = GlobalUtils.getScreenWidth(context);
        nodeTopOffset = (int) (timeTextSize - nodeSize) + PixelUtils.dip2px(context, 2);
        titleDesSpacing = PixelUtils.dip2px(context, 6);
        flowTrackViewUtils.onPreview();
    }

    public void clear() {
        flowTrackViewUtils.getFlowTrackOneItems().clear();
        flowTrackViewUtils.getCanvasList().clear();
    }

    public void bind(List<FlowTrackOneItem> flowTrackOneItems) {
        flowTrackViewUtils.setFlowTrackOneItems(flowTrackOneItems);
        this.requestLayout();
        this.postInvalidate();
    }

    private FlowTrackViewUtils flowTrackViewUtils = new FlowTrackViewUtils() {
        @Override
        protected void onPreview() {
            super.onPreview();
        }

        @Override
        protected void onDrawView() {
            try {
                int top = PixelUtils.px2dip(getContext(), 10);
                for (int i = 0; i < flowTrackViewUtils.getFlowTrackOneItems().size(); i++) {
                    FlowTrackOneItem flowTrackOneItem = flowTrackViewUtils.getFlowTrackOneItems().get(i);
                    //绘制时间
                    int drawTimeTextHeight = drawTimeText(flowTrackOneItem, timeTextPaint, i);
                    //绘制节点
                    drawNode(flowTrackOneItem, i);
                    //绘制右边文本
                    drawRightText(flowTrackOneItem);
                    flowTrackViewUtils.setContentHeight(flowTrackViewUtils.getContentHeight() + drawTimeTextHeight);
                }
                flowTrackViewUtils.setContentHeight(flowTrackViewUtils.getContentHeight() + paddingTop + top);
                //绘制节点连接线
                drawNodeLine();
                flowTrackViewUtils.setContentHeight(flowTrackViewUtils.getContentHeight() + paddingBottom);
            } catch (Exception e) {
                Logger.L.error(e);
            }
        }
    };

    private void drawRightText(FlowTrackOneItem flowTrackOneItem) {
        //获取当前节点
        Integer key = nodeKeys.get(nodeKeys.size() - 1);
        FlowTrackNodeItem nodeItem = (FlowTrackNodeItem) flowTrackViewUtils.getCanvasList().get(key);
        int textWidth = screenWidth - paddingLeft - leftViewWidth - leftTextNodeSpacing - nodeSize - rightTextNodeSpacing - paddingRight - desTextSize;
        //绘制标题
        StaticLayout textsl = new StaticLayout(
                flowTrackOneItem.getTitle(),
                titlePaint,
                textWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1,
                0,
                true);
        CanvasParams canvasParams = new CanvasParams();
        canvasParams.setDx(nodeItem.getCx() + nodeSize / 2 + rightTextNodeSpacing);
        canvasParams.setDy(nodeItem.getCy() - nodeSize / 2 - titleTextSize);
        canvasParams.setStaticLayout(textsl);
        int count = flowTrackViewUtils.getKeys().size() + 1;
        flowTrackViewUtils.getKeys().add(count);
        flowTrackViewUtils.getCanvasList().put(count, canvasParams);
        //绘制描述
        StaticLayout dessl = new StaticLayout(
                flowTrackOneItem.getDes(),
                desPaint,
                textWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1,
                0,
                true);
        CanvasParams desCanvasParams = new CanvasParams();
        desCanvasParams.setDx(nodeItem.getCx() + nodeSize / 2 + rightTextNodeSpacing);
        desCanvasParams.setDy(canvasParams.getDy() + titleTextSize + titleDesSpacing);
        desCanvasParams.setStaticLayout(dessl);
        int desKey = flowTrackViewUtils.getKeys().size() + 1;
        flowTrackViewUtils.getKeys().add(desKey);
        flowTrackViewUtils.getCanvasList().put(desKey, desCanvasParams);
        //改变时间纵坐标
        if (timeKeys.size() > 1) {
            Integer timeKey = timeKeys.get(timeKeys.size() - 1);
            CanvasParams timeCanvasParams = (CanvasParams) flowTrackViewUtils.getCanvasList().get(timeKey);
            int desDy = desTextSize * dessl.getLineCount() + desCanvasParams.getDy();
            if (desDy > timeCanvasParams.getDy()) {
                int timeHeight = (int) (timeCanvasParams.getStaticLayout().getLineCount() * timeTextSize);
                int offset = Math.abs(desDy - timeCanvasParams.getDy() - timeHeight);
                timeCanvasParams.setDy(timeCanvasParams.getDy() + offset);
                flowTrackViewUtils.setContentHeight(flowTrackViewUtils.getContentHeight() + offset);
            }
        }
    }

    private void drawNode(FlowTrackOneItem flowTrackOneItem, int position) {
        Integer timeKey = timeKeys.get(timeKeys.size() - 1);
        CanvasParams canvasParams = (CanvasParams) flowTrackViewUtils.getCanvasList().get(timeKey);
        FlowTrackNodeItem nodeItem = new FlowTrackNodeItem();
        nodeItem.setCx(canvasParams.getDx() + leftViewWidth + leftTextNodeSpacing + nodeSize / 2);
        nodeItem.setCy(canvasParams.getDy() + nodeSize);
        nodeItem.setRadius(nodeSize / 2);
        if (flowTrackOneItem.isChk()) {
            drawCircle(nodeItem.getCx(),
                    nodeItem.getCy(),
                    nodeSize / 2,
                    expressCircleSelDarkPaint);
            drawCircle(nodeItem.getCx(),
                    nodeItem.getCy(),
                    nodeItem.getRadius() - PixelUtils.dip2px(getContext(), 2),
                    expressCircleSelWhitePaint);
            int nodeKey = drawCircle(nodeItem.getCx(),
                    nodeItem.getCy(),
                    nodeItem.getRadius() - PixelUtils.dip2px(getContext(), 4),
                    expressCircleSelDarkPaint);
            nodeKeys.add(nodeKey);
        } else {
            int nodeKey = drawCircle(nodeItem.getCx(), nodeItem.getCy(), nodeItem.getRadius(), expressCirclePaint);
            nodeKeys.add(nodeKey);
        }
    }

    private void drawNodeLine() {
        try {
            if (!ObjectJudge.isNullOrEmpty(nodeKeys)) {
                int startx = 0;
                int starty = 0;
                int stopx = 0;
                int stopy = 0;
                Integer firstKey = nodeKeys.get(0);
                Object first = flowTrackViewUtils.getCanvasList().get(firstKey);
                if (first != null && first instanceof FlowTrackNodeItem) {
                    FlowTrackNodeItem nodeItem = (FlowTrackNodeItem) first;
                    startx = nodeItem.getCx();
                    starty = nodeItem.getCy() + nodeSize / 2;
                }
                Integer key = nodeKeys.get(nodeKeys.size() - 1);
                Object o = flowTrackViewUtils.getCanvasList().get(key);
                if (o != null && o instanceof FlowTrackNodeItem) {
                    FlowTrackNodeItem nodeItem = (FlowTrackNodeItem) o;
                    stopx = nodeItem.getCx();
                    stopy = nodeItem.getCy() + buttomOffset + paddingBottom;
                }
                int count = flowTrackViewUtils.getKeys().size() + 1;
                flowTrackViewUtils.getKeys().add(count);
                FlowTrackLineItem lineItem = new FlowTrackLineItem();
                lineItem.setStartX(startx);
                lineItem.setStartY(starty);
                lineItem.setStopX(stopx);
                lineItem.setStopY(stopy);
                lineItem.setPaint(expressCirclePaint);
                flowTrackViewUtils.getCanvasList().put(count, lineItem);
            }
            nodeKeys.clear();
            timeKeys.clear();
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private int drawCircle(int cx, int cy, int radius, Paint paint) {
        FlowTrackNodeItem nodeItem = new FlowTrackNodeItem();
        nodeItem.setCx(cx);
        nodeItem.setCy(cy);
        nodeItem.setRadius(radius);
        nodeItem.setPaint(paint);
        TrackItemCircleType circleType = new TrackItemCircleType();
        circleType.setX(nodeItem.getCx());
        circleType.setY(nodeItem.getCy());
        circleType.setRadius(nodeItem.getRadius());
        int count = flowTrackViewUtils.getKeys().size() + 1;
        flowTrackViewUtils.getKeys().add(count);
        flowTrackViewUtils.getCanvasList().put(count, nodeItem);
        return count;
    }

    private int drawTimeText(FlowTrackOneItem flowTrackOneItem, TextPaint textPaint, int position) {
        if (flowTrackOneItem.isChk()) {
            textPaint.setColor(timeSelectTextColor);
        } else {
            textPaint.setColor(timeTextColor);
        }
        StaticLayout textsl = new StaticLayout(
                flowTrackOneItem.getTime(),
                textPaint,
                leftViewWidth,
                Layout.Alignment.ALIGN_OPPOSITE,
                1,
                0,
                true);
        CanvasParams canvasParams = new CanvasParams();
        canvasParams.setDx(paddingLeft);
        if (position == 0) {
            canvasParams.setDy(paddingTop + titleTextSize);
        } else {
            Integer timeKey = timeKeys.get(timeKeys.size() - 1);
            CanvasParams prevCanvasParams = (CanvasParams) flowTrackViewUtils.getCanvasList().get(timeKey);
            canvasParams.setDy(prevCanvasParams.getDy() +
                    perFlowItemSpacing +
                    (int) timeTextSize * textsl.getLineCount() +
                    nodeSize);
        }
        canvasParams.setStaticLayout(textsl);
        int count = flowTrackViewUtils.getKeys().size() + 1;
        flowTrackViewUtils.getKeys().add(count);
        timeKeys.add(count);
        flowTrackViewUtils.getCanvasList().put(count, canvasParams);
        return perFlowItemSpacing + (int) timeTextSize * textsl.getLineCount();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        flowTrackViewUtils.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = flowTrackViewUtils.onMeasure() + paddingTop + paddingBottom + buttomOffset;
        setMeasuredDimension(widthMeasureSpec, height);
    }
}
