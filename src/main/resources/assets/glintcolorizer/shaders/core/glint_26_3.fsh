#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>

uniform sampler2D Sampler0;

layout(location = 0) in float sphericalVertexDistance;
layout(location = 1) in float cylindricalVertexDistance;
layout(location = 2) in vec2 texCoord0;
layout(location = 3) in vec4 vertexColor;

layout(location = 0) out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;
    float fade = 1.0 - total_fog_value(sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd);
    fragColor = vec4(color.rgb * fade, color.a);
}
