uniform sampler2D u_texture;
uniform float u_time;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    if (abs(v_texCoords.y - fract(u_time / 5.)) < .05) {
        v_texCoords.x += (v_texCoords.y - fract(u_time / 5.)) / 20.;
    }

    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}