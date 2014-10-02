package rabbitescape.ui.android;

import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import java.util.Map;

import rabbitescape.engine.LoadWorldFile;
import rabbitescape.engine.Token;
import rabbitescape.engine.World;
import rabbitescape.engine.util.RealFileSystem;
import rabbitescape.render.BitmapCache;


public class AndroidGameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_game );
        LinearLayout topLayout = (LinearLayout)findViewById( R.id.topLayout );

        World world = new LoadWorldFile( new RealFileSystem() ).load( "test/level_01.rel" );

        Resources resources = getResources();

        createAbilityButtons( world, resources );

        BitmapCache<AndroidBitmap> bitmapCache = createBitmapCache( resources );

        topLayout.addView( new MySurfaceView( this, bitmapCache, world ) );
    }

    private void createAbilityButtons( World world, Resources resources )
    {
        RadioGroup abilitiesGroup = (RadioGroup)findViewById( R.id.abilitiesGroup );
        for ( Token.Type ability : world.abilities.keySet() )
        {
            String name = ability.name();

            ImageButton imgButton = new ImageButton( this );
            imgButton.setImageDrawable(
                resources.getDrawable(
                    resources.getIdentifier(
                        "ability_" + name,
                        "drawable",
                        "rabbitescape.ui.android"
                    )
                )
            );

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            imgButton.setLayoutParams( params );
            imgButton.setContentDescription( name );
            abilitiesGroup.addView( imgButton );
        }
    }

    private BitmapCache<AndroidBitmap> createBitmapCache( Resources resources )
    {
        return new BitmapCache<AndroidBitmap>( new AndroidBitmapLoader( resources ), 500 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
