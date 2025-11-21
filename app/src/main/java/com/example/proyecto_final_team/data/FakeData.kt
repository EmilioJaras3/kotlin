package com.example.proyecto_final_team.data

import com.example.proyecto_final_team.model.LiveClass
import com.example.proyecto_final_team.model.Routine
import com.example.proyecto_final_team.model.Trainer

object FakeData {
    val trainers = listOf(
        Trainer(1, "Anna Lee", "Instructora de yoga", "https://randomuser.me/api/portraits/women/44.jpg"),
        Trainer(2, "Alex Roy", "Entrenador personal", "https://randomuser.me/api/portraits/men/32.jpg"),
        Trainer(3, "Jane Doe", "Coach de meditación", "https://randomuser.me/api/portraits/women/68.jpg"),
        Trainer(4, "John Smith", "Experto en respiración", "https://randomuser.me/api/portraits/men/11.jpg")
    )

    val routines = mutableListOf(
        Routine(
            1, "Yoga", "Yoga", "15 min", "Beginner", "Nada",
            "Comienza tu día con energía y concentración. Este flujo suave está diseñado para despertar tu cuerpo y mente, ayudándote a conectarte con tu respiración y establecer una intención positiva para el día que tienes por delante. Perfecto para todos los niveles.",
            trainers[0],
            "https://images.unsplash.com/photo-1544367563-12123d8965cd?q=80&w=2070&auto=format&fit=crop",
            isFavorite = true
        ),
        Routine(
            2, "Entrena tu core", "Ejercicio", "25 min", "Intermediate", "Mat",
            "Fortalece tu abdomen y espalda baja con esta rutina intensa.",
            trainers[1],
            "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?q=80&w=2070&auto=format&fit=crop",
            isFavorite = true
        ),
        Routine(
            3, "Despeja tu mente", "Relajación", "5 min", "All levels", "None",
            "Una breve pausa para reconectar contigo mismo.",
            trainers[2],
            "https://images.unsplash.com/photo-1506126613408-eca07ce68773?q=80&w=1999&auto=format&fit=crop",
            isFavorite = true
        ),
        Routine(
            4, "Estira tu cuerpo", "Yoga", "30 min", "Beginner", "Mat",
            "Estiramientos profundos para mejorar la flexibilidad.",
            trainers[0],
            "https://images.unsplash.com/photo-1552196563-55cd4e45efb3?q=80&w=2026&auto=format&fit=crop",
            isFavorite = true
        ),
        Routine(
            5, "Meditación guiada", "Meditación", "10 min", "All levels", "Cushion",
            "Encuentra paz interior con esta guía paso a paso.",
            trainers[2],
            "https://images.unsplash.com/photo-1528319725582-ddc096101511?q=80&w=2069&auto=format&fit=crop",
            isFavorite = false
        )
    )

    val liveClasses = listOf(
        LiveClass(1, "Despertando con yoga", "Alex Roy", "Now", true, "https://images.unsplash.com/photo-1599901860904-17e6ed7083a0?q=80&w=2069&auto=format&fit=crop"),
        LiveClass(2, "Meditacion", "Jane Doe", "10:00 am", false, "https://images.unsplash.com/photo-1593811167562-9cef47bfc4d7?q=80&w=2072&auto=format&fit=crop"),
        LiveClass(3, "Respiraciones", "John Smith", "12:00 pm", false, "https://images.unsplash.com/photo-1506126613408-eca07ce68773?q=80&w=1999&auto=format&fit=crop")
    )
}
