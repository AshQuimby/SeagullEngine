uniform sampler2D u_texture;
uniform float u_time;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    vec2 uv = v_texCoords;
    uv -= .5;
    uv *= 2.;
    v_texCoords.x *= abs(uv.x);

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}