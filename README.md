Action Tooltip [ ![Download](https://api.bintray.com/packages/kdrakonakis/maven/tooltip-action/images/download.svg) ](https://bintray.com/kdrakonakis/maven/tooltip-action/_latestVersion)

A custom Tooltip that can be anchored to a view. By default it supports TextView only but you can setCustomView with your custom layout.xml file.

![alt tag](https://github.com/kostasdrakonakis/tooltip_action/blob/master/button_tooltip.PNG)
![alt tag](https://github.com/kostasdrakonakis/tooltip_action/blob/master/menu_item.PNG)
![alt tag](https://github.com/kostasdrakonakis/tooltip_action/blob/master/email_link.png)


Download
--------

Download the latest JAR or grab via Maven:
```xml
<dependency>
  <groupId>com.github.kostasdrakonakis</groupId>
  <artifactId>tooltip-action</artifactId>
  <version>1.4.1</version>
</dependency>
```
or Gradle:
```groovy
implementation 'com.github.kostasdrakonakis:tooltip-action:1.4.1'
```

Usage
-----

```java
ActionTooltip.anchorAt(this, findViewById(R.id.my_anchor_view_id))
                        .padding(100, 100, 100, 100)
                        .show();
//Attach to Menu Item
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.sample_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                View view = findViewById(R.id.action_item2);

                ActionTooltip.anchorAt(MainActivity.this, view)
                        .padding(100, 100, 100, 100)
                        .setPositionTo(TooltipPosition.BOTTOM)
                        .setCustomView(R.layout.tooltip_view)
                        .show();
            }
        });
        return true;
    }
    
// Add Display Listener
  ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
          .padding(100, 100, 100, 100)
          .setPositionTo(TooltipPosition.TOP)
          .setForeverVisible(true)
          .setCustomView(customView)
          .setDisplayListener(new DisplayListener() {
              @Override
              public void onViewDisplayed(View view) {
                  Toast.makeText(
                          MainActivity.this, "I am displayed", Toast.LENGTH_SHORT)
                          .show();
              }

              @Override
              public void onViewHidden(View view) {
                  Toast.makeText(
                          MainActivity.this, "I am hidden", Toast.LENGTH_SHORT)
                          .show();
              }
          })
          .show();
          
//Set forever visible and hide on Action
    View customView = getLayoutInflater().inflate(R.layout.tooltip_view, null);

    final TooltipActionView tooltipActionView =
            ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
                    .padding(100)
                    .setPositionTo(TooltipPosition.TOP)
                    .setForeverVisible(true)
                    .setCustomView(customView)
                    .show();

    customView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tooltipActionView.hide();
        }
    });
    //Add AutoLink Mask
    ActionTooltip.anchorAt(this, R.id.fragment_text)
                    .setForeverVisible(true)
                    .setPadding(50)
                    .setDrawableRight(R.drawable.ic_backup_white_24dp)
                    .setAutoLinkMask(Linkify.EMAIL_ADDRESSES)
                    .setTextColorId(R.color.colorPrimaryDark)
                    .setLinkTextColor(Color.WHITE)
                    .setText("Hello from Fragment email@example.com").show();
```

Setting custom view:


```java
View view = getLayoutInflater().inflate(R.layout.tooltip_view, null);
ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
            .padding(100, 100, 100, 100)
            .setPositionTo(TooltipPosition.TOP)
            .setCustomView(view)
            .show();
            
//or by layoutId
ActionTooltip.anchorAt(this, findViewById(R.id.hello_world))
            .padding(100, 100, 100, 100)
            .setPositionTo(TooltipPosition.TOP)
            .setCustomView(R.layout.tooltip_view)
            .show();
```

Changelog
-------------
v1.0.0
- `autoHide(boolean autoHide, long duration)`
- `hideOnClick(boolean clickToHide)`
- `padding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- `setAnimation(FadeAnimation tooltipAnimation)`
- `setBackgroundColor(@ColorInt int color)`
- `setCustomView(View customView)`
- `setDisplayListener(DisplayListener listener)`
- `setDuration(long duration)`
- `setForeverVisible(boolean foreverVisible)`
- `setPositionTo(TooltipPosition position)`
- `setText(String text)`
- `setTextAlignment(TooltipAlign align)`
- `setTextColor(int textColor)`
- `setTextFont(Typeface typeface)`
- `setTextGravity(int textGravity)`
- `setTextSize(int unit, float textSize)`
- `show()`

v1.1.0
- `setAllCaps(boolean allCaps)`
- `setArrowHeight(int arrowHeight)`
- `setArrowHeightId(@IntegerRes int arrowHeightId)`
- `setCustomView(@LayoutRes int layoutId)` Sets a custom view. See Usage above
- `setMaxLines(int maxLines)`
- `setMaxWidth(int maxPixels)`
- `setMinLines(int minLines)`
- `setMovementMethod(MovementMethod movementMethod)`
- `setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- `setTextColor(@ColorInt int color)`
- `setTextColor(@NonNull String colorString)`
- `setTextColorId(@ColorRes int colorId)`
- `setTypeFace(Typeface typeface)`

v1.2.0
- `setBackgroundColorId(@ColorRes int colorId)`
- `setElevation(float elevation)`
- `setEllipsize(TextUtils.TruncateAt atWhere)`
- `setEms(int ems)`
- `setError(CharSequence error)`
- `setFilters(InputFilter[] filters)`
- `setHint(@StringRes int hintId)`
- `setHint(CharSequence hint)`
- `setHintTextColor(@ColorInt int color)`
- `setHorizontallyScrolling(boolean whether)`
- `setSingleLine(boolean singleLine)`
- `setText(@StringRes int stringId)`
- `setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled)`

v1.3.0
- `anchorAt(Context context, @IdRes int viewId)`
- `setDrawableBottom(@DrawableRes int drawableId)`
- `setDrawableLeft(@DrawableRes int drawableId)`
- `setDrawablePadding(int drawablePadding)`
- `setDrawableRight(@DrawableRes int drawableId)`
- `setDrawableTop(@DrawableRes int drawableId)`
- `setLetterSpacing(float letterSpacing)`

v1.4.0
- `setCornerRadius(int cornerRadius)`
- `setCornerRadiusId(@IntegerRes int cornerRadiusId)`
- `setError(@StringRes int stringId)`
- `setOnClickListener(View.OnClickListener onClickListener)`
- `setOnLongClickListener(View.OnLongClickListener onLongClickListener)`

v1.4.1
- `setAutoLinkMask(@LinkifyCompat.LinkifyMask int mask)`
- `setDuration(int duration)`
- `setDurationId(@IntegerRes int durationId)`
- `setHintTextColor(@NonNull String color)`
- `setLinkTextColor(@ColorInt int color)`
- `setLinkTextColor(@NonNull String color)`
- `setLinkTextColorId(@ColorRes int colorId)`
- `setPadding(@Px int padding)`
- `setPaddingBottom(@Px int bottom)`
- `setPaddingLeft(@Px int left)`
- `setPaddingRight(@Px int right)`
- `setPaddingTop(@Px int top)`

Useful methods
-------------

You can use the following methods:

- `autoHide(boolean autoHide, long duration)`
- `hideOnClick(boolean clickToHide)`
- `setAllCaps(boolean allCaps)`
- `setAnimation(FadeAnimation tooltipAnimation)`
- `setArrowHeight(int arrowHeight)`
- `setArrowHeightId(@IntegerRes int arrowHeightId)`
- `setAutoLinkMask(@LinkifyCompat.LinkifyMask int mask)`
- `setBackgroundColor(@ColorInt int color)`
- `setBackgroundColorId(@ColorRes int colorId)`
- `setCornerRadius(int cornerRadius)`
- `setCornerRadiusId(@IntegerRes int cornerRadiusId)`
- `setCustomView(@LayoutRes int layoutId)`
- `setCustomView(View customView)`
- `setDisplayListener(DisplayListener listener)`
- `setDrawableBottom(@DrawableRes int drawableId)`
- `setDrawableLeft(@DrawableRes int drawableId)`
- `setDrawablePadding(int drawablePadding)`
- `setDrawableRight(@DrawableRes int drawableId)`
- `setDrawableTop(@DrawableRes int drawableId)`
- `setDuration(int duration)`
- `setDuration(long duration)`
- `setDurationId(@IntegerRes int durationId)`
- `setElevation(float elevation)`
- `setEllipsize(TextUtils.TruncateAt atWhere)`
- `setEms(int ems)`
- `setError(@StringRes int stringId)`
- `setError(CharSequence error)`
- `setFilters(InputFilter[] filters)`
- `setForeverVisible(boolean foreverVisible)`
- `setHint(@StringRes int hintId)`
- `setHint(CharSequence hint)`
- `setHintTextColor(@ColorInt int color)`
- `setHintTextColor(@NonNull String color)`
- `setHorizontallyScrolling(boolean whether)`
- `setLetterSpacing(float letterSpacing)`
- `setLinkTextColor(@ColorInt int color)`
- `setLinkTextColor(@NonNull String color)`
- `setLinkTextColorId(@ColorRes int colorId)`
- `setMaxLines(int maxLines)`
- `setMaxWidth(int maxPixels)`
- `setMinLines(int minLines)`
- `setMovementMethod(MovementMethod movementMethod)`
- `setOnClickListener(View.OnClickListener onClickListener)`
- `setOnLongClickListener(View.OnLongClickListener onLongClickListener)`
- `setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom)`
- `setPadding(@Px int padding)`
- `setPaddingBottom(@Px int bottom)`
- `setPaddingLeft(@Px int left)`
- `setPaddingRight(@Px int right)`
- `setPaddingTop(@Px int top)`
- `setPositionTo(TooltipPosition position)`
- `setSingleLine(boolean singleLine)`
- `setText(@StringRes int stringId)`
- `setText(String text)`
- `setTextAlignment(TooltipAlign align)`
- `setTextColor(@ColorInt int color)`
- `setTextColor(@NonNull String colorString)`
- `setTextColor(int textColor)`
- `setTextColorId(@ColorRes int colorId)`
- `setTextGravity(int textGravity)`
- `setTextSize(int unit, float textSize)`
- `setTypeFace(Typeface typeface)`
- `setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled)`
- `show()`

License
-------

 Copyright 2017 Kostas Drakonakis

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
