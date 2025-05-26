<template>
  <div class="container">
    <h1>Gestor de Tareas</h1>

    <!-- Formulario para crear o actualizar una tarea -->
    <form @submit.prevent="guardar">
      <input v-model="form.titulo" placeholder="Título" required />
      <input v-model="form.descripcion" placeholder="Descripción" />
      <select v-model="form.prioridad" required>
        <option value="">Prioridad</option>
        <option value="ALTA">Alta</option>
        <option value="MEDIA">Media</option>
        <option value="BAJA">Baja</option>
      </select>
      <input type="date" v-model="form.fechaLimite" />
      <button type="submit">{{ form.id ? 'Actualizar' : 'Crear' }}</button>
    </form>

    <!-- Filtros para búsqueda por prioridad y fecha -->
    <h2>Filtros</h2>
    <select v-model="filtroPrioridad">
      <option value="">Todas las prioridades</option>
      <option value="ALTA">Alta</option>
      <option value="MEDIA">Media</option>
      <option value="BAJA">Baja</option>
    </select>

    <input type="date" v-model="filtroFecha" />

    <!-- Lista de tareas mostradas -->
    <h2>Lista de Tareas</h2>
    <ul>
      <li v-for="tarea in tareasFiltradas" :key="tarea.id">
        <!-- Casilla de completada, que llama a la función toggleCompletada -->
        <input
          type="checkbox"
          :checked="tarea.completada"
          @change="() => toggleCompletada(tarea)"
        />
        <!-- Estilo tachado si la tarea está completada -->
        <span :style="{ textDecoration: tarea.completada ? 'line-through' : 'none' }">
          <strong>{{ tarea.titulo }}</strong>
          <br>
          {{ tarea.prioridad }} - {{ tarea.fechaLimite }}
        </span>
        <br />
        {{ tarea.descripcion }}
        <br />
        <button @click="editarTarea(tarea)">Editar</button>
        <button @click="eliminarTarea(tarea.id)">Eliminar</button>
        <br><br>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

// URL base del backend
const API_URL = 'http://localhost:8080/api/tareas'

// Lista reactiva de tareas
const tareas = ref([])

// Objeto reactivo para el formulario
const form = ref({
  titulo: '',
  descripcion: '',
  prioridad: '',
  fechaLimite: '',
  completada: false,
  id: null
})

// Cambia el estado de completada en el servidor y actualiza en la vista
const toggleCompletada = async (tarea) => {
  try {
    await fetch(`http://localhost:8080/tareas/${tarea.id}/completada`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(!tarea.completada),
    });
    tarea.completada = !tarea.completada;
  } catch (error) {
    console.error("Error al actualizar tarea:", error);
  }
};

// Variables reactivas para filtros
const filtroPrioridad = ref('')
const filtroFecha = ref('')

// Carga todas las tareas desde el backend al iniciar
const cargarTareas = async () => {
  const res = await axios.get(API_URL)
  tareas.value = res.data
}

// Crea o actualiza una tarea según si existe un ID
const guardar = async () => {
  if (form.value.id) {
    await axios.put(`${API_URL}/${form.value.id}`, form.value)
  } else {
    await axios.post(API_URL, form.value)
  }

  // Limpia el formulario después de guardar
  form.value = {
    titulo: '',
    descripcion: '',
    prioridad: '',
    fechaLimite: '',
    completada: false,
    id: null
  }

  cargarTareas()
}

// Llena el formulario con los datos de la tarea seleccionada
const editarTarea = (tarea) => {
  form.value = { ...tarea }
}

// Elimina la tarea por ID y recarga la lista
const eliminarTarea = async (id) => {
  await axios.delete(`${API_URL}/${id}`)
  cargarTareas()
}

// (No se usa en el template, pero sirve para actualizar la tarea directamente)
const actualizarTarea = async (tarea) => {
  await axios.put(`${API_URL}/${tarea.id}`, tarea)
  cargarTareas()
}

// Filtra tareas por prioridad y/o fecha
const tareasFiltradas = computed(() => {
  return tareas.value.filter(t => {
    const coincidePrioridad = !filtroPrioridad.value || t.prioridad === filtroPrioridad.value
    const coincideFecha = !filtroFecha.value || t.fechaLimite === filtroFecha.value
    return coincidePrioridad && coincideFecha
  })
})

// Se ejecuta al montar el componente para cargar las tareas existentes
onMounted(cargarTareas)
</script>

<style>
/**Estilos Css */

.container {
  max-width: 600px;
  margin: auto;
  font-family: sans-serif;
}

form {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 20px;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: #063b70;
  padding: 1rem;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
}
</style>
