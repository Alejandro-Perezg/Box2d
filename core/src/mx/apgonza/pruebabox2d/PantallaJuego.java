package mx.apgonza.pruebabox2d;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaJuego extends Pantalla {
    private static final float RADIO =10f ;
    private World mundo;
    private Body body;
    private Box2DDebugRenderer debugRenderer;
    //Mapa
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    public PantallaJuego(Juego juego) {

    }

    @Override
    public void show() {
        crearMundo();
        crearObjetos();
        cargarMapa();
        definirParedes();
    }

    private void definirParedes() {
        ConvertidorMapa.crearCuerpos(mapa,mundo);

    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver())); //interpreta el achivo del mapa
        manager.load("mapaMario.tmx", TiledMap.class);
        manager.finishLoading();   //Segundo plano los elementos}
        mapa=manager.get("mapaMario.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearObjetos() {
    //BodyDef
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(5,700);
        body = mundo.createBody(bodydef);

        CircleShape circulo = new CircleShape();
        circulo.setRadius(RADIO);
    //Define propiedades fisicas
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circulo;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);

        circulo.dispose();

    //PISO - PLATAFORMA
        BodyDef bodyPisoDef = new BodyDef();
        bodyPisoDef.type = BodyDef.BodyType.StaticBody;
        bodyPisoDef.position.set(ANCHO/4, 10);

        Body bodyPiso = mundo.createBody(bodyPisoDef);

        PolygonShape pisoShape = new PolygonShape();
        pisoShape.setAsBox(ANCHO/4, 10);
        bodyPiso.createFixture(pisoShape,0);

        pisoShape.dispose();

    }

    private void crearMundo() {
        Box2D.init();
        Vector2 gravedad = new Vector2(0,-100);
        mundo = new World(gravedad, true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        batch.setProjectionMatrix(camara.combined);
        rendererMapa.setView(camara);

        rendererMapa.render();


        debugRenderer.render(mundo, camara.combined);
    //final
        mundo.step(1/60f,6,2);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

