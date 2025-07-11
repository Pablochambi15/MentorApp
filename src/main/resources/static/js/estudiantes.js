document.addEventListener('DOMContentLoaded', function() {
    // Datos de ejemplo (reemplazar con llamadas a tu API)
    const recommendedMentors = [
        {
            id: 1,
            name: "Ana Pérez",
            specialty: "Ingeniería de Software",
            profileImage: "https://i.imgur.com/1.png",
            rating: 4.5,
            reviewCount: 12
        },
        {
            id: 2,
            name: "Carlos Rodríguez",
            specialty: "Medicina",
            profileImage: "https://i.imgur.com/2.png",
            rating: 4.8,
            reviewCount: 8
        },
        {
            id: 3,
            name: "Lucía Fernández",
            specialty: "Derecho",
            profileImage: "https://i.imgur.com/3.png",
            rating: 4.2,
            reviewCount: 5
        }
    ];

    const upcomingSessions = [
        {
            id: 1,
            mentor: {
                id: 1,
                name: "Ana Pérez"
            },
            topic: "Programación Avanzada",
            dateTime: new Date(Date.now() + 86400000), // Mañana
            meetingLink: "https://meet.google.com/abc123"
        }
    ];

    // Cargar mentores recomendados
    const mentorGrid = document.getElementById('mentorGrid');
    
    if (mentorGrid) {
        if (recommendedMentors.length > 0) {
            mentorGrid.innerHTML = recommendedMentors.map(mentor => `
                <div class="col">
                    <div class="card mentor-card h-100">
                        <img src="${mentor.profileImage}" class="card-img-top mentor-img" alt="${mentor.name}">
                        <div class="card-body">
                            <h5 class="card-title">${mentor.name}</h5>
                            <p class="card-text text-muted">${mentor.specialty}</p>
                            <div class="mb-2">
                                ${renderStars(mentor.rating)}
                                <small class="text-muted">(${mentor.reviewCount})</small>
                            </div>
                            <a href="/student/mentors/${mentor.id}" class="btn btn-sm btn-primary">
                                Ver perfil
                            </a>
                        </div>
                    </div>
                </div>
            `).join('');
        } else {
            mentorGrid.innerHTML = `
                <div class="col-12 text-center py-4">
                    <i class="fas fa-user-graduate fa-3x text-muted mb-3"></i>
                    <p class="text-muted">No hay mentores recomendados disponibles</p>
                    <a href="/student/mentors" class="btn btn-primary">
                        Explorar mentores
                    </a>
                </div>
            `;
        }
    }

    // Cargar próximas sesiones
    const sessionsList = document.getElementById('sessionsList');
    const emptySessionsMessage = document.getElementById('emptySessionsMessage');
    
    if (sessionsList && emptySessionsMessage) {
        if (upcomingSessions.length > 0) {
            emptySessionsMessage.style.display = 'none';
            sessionsList.innerHTML = upcomingSessions.map(session => `
                <div class="list-group-item session-item">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="mb-1 session-mentor">${session.mentor.name}</h6>
                            <small class="text-muted session-topic">${session.topic}</small>
                        </div>
                        <div class="text-end">
                            <div class="session-date">${formatDate(session.dateTime)}</div>
                            <a href="${session.meetingLink}" class="btn btn-sm btn-success mt-2 btn-join" target="_blank">
                                <i class="fas fa-video me-1"></i> Unirse
                            </a>
                        </div>
                    </div>
                </div>
            `).join('');
        } else {
            sessionsList.style.display = 'none';
            emptySessionsMessage.style.display = 'block';
        }
    }

    // Función para renderizar estrellas de valoración
    function renderStars(rating) {
        let stars = '';
        for (let i = 1; i <= 5; i++) {
            stars += `<i class="${i <= rating ? 'fas' : 'far'} fa-star ${i <= rating ? 'text-warning' : 'text-muted'}"></i>`;
        }
        return stars;
    }

    // Función para formatear fecha
    function formatDate(date) {
        const options = { 
            day: '2-digit', 
            month: '2-digit', 
            year: 'numeric',
            hour: '2-digit', 
            minute: '2-digit' 
        };
        return date.toLocaleDateString('es-ES', options);
    }

    // Inicializar tooltips de Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});