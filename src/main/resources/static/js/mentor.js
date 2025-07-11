/**
 * mentores/base.js - Funcionalidades base para la plantilla de mentores
 * 
 * Este archivo contiene:
 * 1. Inicializaciones generales
 * 2. Funciones de utilidad compartidas
 * 3. Manejo de eventos comunes
 * 4. Configuraciones base para componentes
 */

document.addEventListener('DOMContentLoaded', function() {
    // 1. INICIALIZACIONES GENERALES
    console.log('Cargando plantilla base para mentores...');
    
    // Inicializar tooltips de Bootstrap
    initTooltips();
    
    // Inicializar popovers de Bootstrap
    initPopovers();
    
    // Configurar manejadores de eventos
    setupEventHandlers();
    
    // 2. FUNCIONALIDADES ESPECÍFICAS PARA MENTORES
    setupMentorSpecificFeatures();
});

/**
 * Inicializa los tooltips de Bootstrap
 */
function initTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl, {
            trigger: 'hover'
        });
    });
}

/**
 * Inicializa los popovers de Bootstrap
 */
function initPopovers() {
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
}

/**
 * Configura manejadores de eventos globales
 */
function setupEventHandlers() {
    // Manejar clicks en enlaces activos
    document.querySelectorAll('a.nav-link').forEach(link => {
        link.addEventListener('click', function() {
            document.querySelectorAll('a.nav-link').forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        });
    });
    
    // Manejar el logout
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            performLogout();
        });
    }
}

/**
 * Configura características específicas para mentores
 */
function setupMentorSpecificFeatures() {
    // Notificaciones para mentores
    setupMentorNotifications();
    
    // Inicializar el selector de disponibilidad
    initAvailabilitySelector();
    
    // Configurar el menú de perfil de mentor
    setupMentorProfileMenu();
}

/**
 * Configura el sistema de notificaciones para mentores
 */
function setupMentorNotifications() {
    const notificationBell = document.getElementById('mentor-notification-bell');
    if (notificationBell) {
        notificationBell.addEventListener('click', function() {
            fetchUnreadNotifications();
        });
        
        // Comprobar notificaciones cada 5 minutos
        setInterval(fetchUnreadNotifications, 300000);
    }
}

/**
 * Obtiene notificaciones no leídas del servidor
 */
function fetchUnreadNotifications() {
    fetch('/api/mentor/notifications/unread')
        .then(response => response.json())
        .then(data => {
            updateNotificationBadge(data.count);
        })
        .catch(error => {
            console.error('Error al obtener notificaciones:', error);
        });
}

/**
 * Actualiza el badge de notificaciones
 */
function updateNotificationBadge(count) {
    const badge = document.getElementById('notification-badge');
    if (badge) {
        badge.textContent = count;
        badge.style.display = count > 0 ? 'inline-block' : 'none';
    }
}

/**
 * Inicializa el selector de disponibilidad
 */
function initAvailabilitySelector() {
    const availabilityWidget = document.getElementById('availability-widget');
    if (availabilityWidget) {
        // Implementar lógica de selección de disponibilidad
        console.log('Selector de disponibilidad inicializado');
    }
}

/**
 * Configura el menú de perfil del mentor
 */
function setupMentorProfileMenu() {
    const profileMenu = document.getElementById('mentor-profile-menu');
    if (profileMenu) {
        profileMenu.addEventListener('show.bs.dropdown', function() {
            loadMentorProfileSummary();
        });
    }
}

/**
 * Carga el resumen del perfil del mentor
 */
function loadMentorProfileSummary() {
    fetch('/api/mentor/profile/summary')
        .then(response => response.json())
        .then(data => {
            updateProfileMenu(data);
        })
        .catch(error => {
            console.error('Error al cargar resumen del perfil:', error);
        });
}

/**
 * Actualiza el menú de perfil con datos del servidor
 */
function updateProfileMenu(data) {
    const profileName = document.getElementById('profile-name');
    const profileRating = document.getElementById('profile-rating');
    const profileSessions = document.getElementById('profile-sessions');
    
    if (profileName) profileName.textContent = data.name;
    if (profileRating) profileRating.innerHTML = generateRatingStars(data.rating);
    if (profileSessions) profileSessions.textContent = `Sesiones: ${data.completed_sessions}`;
}

/**
 * Genera estrellas de valoración
 */
function generateRatingStars(rating) {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 >= 0.5;
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
    
    let stars = '';
    
    for (let i = 0; i < fullStars; i++) {
        stars += '<i class="fas fa-star"></i>';
    }
    
    if (hasHalfStar) {
        stars += '<i class="fas fa-star-half-alt"></i>';
    }
    
    for (let i = 0; i < emptyStars; i++) {
        stars += '<i class="far fa-star"></i>';
    }
    
    return stars;
}

/**
 * Realiza el logout del mentor
 */
function performLogout() {
    fetch('/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').content
        }
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/login';
        }
    })
    .catch(error => {
        console.error('Error al cerrar sesión:', error);
    });
}

// 3. FUNCIONES DE UTILIDAD PARA MENTORES

/**
 * Formatea una fecha para mostrarla en la UI
 */
function formatDateForDisplay(dateString) {
    const options = { 
        weekday: 'long', 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(dateString).toLocaleDateString('es-ES', options);
}

/**
 * Muestra un toast de notificación
 */
function showMentorNotificationToast(message, type = 'info') {
    const toastContainer = document.getElementById('toast-container');
    if (!toastContainer) return;
    
    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white bg-${type} border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;
    
    toastContainer.insertAdjacentHTML('beforeend', toastHtml);
    
    const toastEl = document.getElementById(toastId);
    const toast = new bootstrap.Toast(toastEl);
    toast.show();
    
    // Eliminar el toast después de que se cierre
    toastEl.addEventListener('hidden.bs.toast', function() {
        toastEl.remove();
    });
}

// Exportar funciones para uso en otros archivos (si se usa módulos)
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        formatDateForDisplay,
        showMentorNotificationToast,
        generateRatingStars
    };
}