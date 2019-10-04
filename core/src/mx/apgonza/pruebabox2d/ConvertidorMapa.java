package mx.apgonza.pruebabox2d;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class ConvertidorMapa
{
    private static final float TAM_BLOQUE = 1;


    public static void crearCuerpos(TiledMap mapa, World mundo) {
        MapObjects objetos = mapa.getLayers().get("PisoPlataforma").getObjects();
        for (MapObject objeto: objetos) {
            Shape rectangulo = getRectangle((RectangleMapObject)objeto);

            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x, ((RectangleMapObject) objeto).getRectangle().y);
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bd);
            body.createFixture(rectangulo, 1);

            rectangulo.dispose();
        }
    }

    private static PolygonShape getRectangle(RectangleMapObject objeto) {
        Rectangle rectangulo = objeto.getRectangle();
        PolygonShape polygon = new PolygonShape();

        Vector2 tam = new Vector2((rectangulo.width*0.5f)/TAM_BLOQUE,
                ( rectangulo.height*0.5f)/TAM_BLOQUE);

        polygon.setAsBox(rectangulo.width*0.5f/TAM_BLOQUE, rectangulo.height*0.5f/TAM_BLOQUE, tam, 0f);
        return polygon;
    }
}