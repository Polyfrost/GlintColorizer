#version 150

#moj_import <minecraft:fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform int GlintColor;

in float vertexDistance;
in vec2 texCoord0;

out vec4 fragColor;

float red(int value) {
    return (value >> 16 & 255) / 255.0F;
}

float green(int value) {
    return (value >> 8 & 255) / 255.0F;
}

float blue(int value) {
    return (value & 255) / 255.0F;
}

vec3 getColor(int value) {
    return vec3(red(value), green(value), blue(value));
}

void main() {
    vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
    if (color.a < 0.1) {
        discard;
    }

    float fade = linear_fog_fade(vertexDistance, FogStart, FogEnd);
    fragColor = vec4((color.rgb * getColor(GlintColor)) * fade, color.a);
}
