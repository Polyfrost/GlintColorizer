#version 150

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>

layout(std140) uniform Glint {
    int GlintColor;
};

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
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

    float fade = (1.0F - total_fog_value(sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd));
    fragColor = vec4((color.rgb * getColor(GlintColor)) * fade, color.a);
}
