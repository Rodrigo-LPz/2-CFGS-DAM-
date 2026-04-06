package com.LopezPerez.dam.taskmanager_pi.model;

// ==================== ENUM ====================
    // Crea un metodo de enumeración para definir los posibles rangos de prioridad de la tarea/actividad en cuestión. Cada valor representa una situación de la propia tarea.
        /*
         * Atributo para el estado de prioridad de la tarea. Los posibles valores del "enum" a utilizar son:
         *   El valor "  BAJA   "   Indica que la tarea es de baja prioridad. Esto es que es una tarea de prioridad baja y que puede ser aplazada o aplazada por otras de mayor rango de prioridad.
         *   El valor "  MEDIA  "   Indica que la tarea es de prioridad media. Esto es que es importante pero no al grado de importancia y urgencia como las tareas de prioridad "Alta".
         *   El valor "  ALTA   "   Indica que la tarea es de maxima prioridad. Esto es que requiere nuestra atención de inmediato dada su urgencia y necesidad.
         */
public enum Priority{ BAJA, MEDIA, ALTA }
